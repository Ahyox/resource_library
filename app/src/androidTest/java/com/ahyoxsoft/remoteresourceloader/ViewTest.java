package com.ahyoxsoft.remoteresourceloader;

import android.content.res.Resources;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.ahyoxsoft.remoteresourceloader.view.activity.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4ClassRunner.class)
public class ViewTest {
    private RecyclerView recyclerView;
    private int itemCount = 0;

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setupTest() {
        MainActivity mainActivity = this.activityRule.getActivity();
        recyclerView = mainActivity.findViewById(R.id.recycler);
        itemCount = recyclerView.getAdapter().getItemCount();

    }

    @Test
    public void RecyclerViewTest() {
        if(this.itemCount > 0) {
            for(int i=0; i < this.itemCount; i++) {

                //Item clicked?
                Espresso.onView(ViewMatchers.withId(R.id.recycler))
                        .perform(RecyclerViewActions.actionOnItemAtPosition(i, ViewActions.click()));

                //View holder displayed?
                Espresso.onView(new RecyclerViewMatcher(R.id.recycler)
                        .atPositionOnView(i, R.id.cardview))
                        .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

            }
        }
    }


    public class RecyclerViewMatcher {

        private final int recyclerViewId;

        public RecyclerViewMatcher(int recyclerViewId) {
            this.recyclerViewId = recyclerViewId;
        }

        public Matcher<View> atPosition(final int position) {
            return atPositionOnView(position, -1);
        }

        public Matcher<View> atPositionOnView(final int position, final int targetViewId) {
            return new TypeSafeMatcher<View>() {

                Resources resources = null;
                View childView;
                public void describeTo(Description description) {
                    String idDescription = Integer.toString(recyclerViewId);
                    if(this.resources != null) {
                        try {
                            idDescription = this.resources.getResourceName(recyclerViewId);
                        } catch (Resources.NotFoundException var4) {
                            idDescription = String.format("%s (resource name not found)",
                                    new Object[] {Integer.valueOf(recyclerViewId) });
                        }
                    }
                    description.appendText("with id: " + idDescription);
                }

                public boolean matchesSafely(View view) {
                    this.resources = view.getResources();
                    if (childView == null) {
                        RecyclerView recyclerView = view.getRootView().findViewById(recyclerViewId);
                        if (recyclerView != null && recyclerView.getId() == recyclerViewId) {
                            childView = recyclerView.findViewHolderForAdapterPosition(position).itemView;
                        } else {
                            return false;
                        }
                    }
                    if (targetViewId == -1) {
                        return view == childView;
                    } else {
                        View targetView = childView.findViewById(targetViewId);
                        return view == targetView;
                    }
                }
            };
        }
    }
}
