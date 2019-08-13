# resource_library

Resource Library is an Android library for downloading resources.

## Installation

For now download and attached the downloader repo to your project.

```bash
implementation project(':downloader')
```

## Usage

```android
import com.ahyoxsoft.downloader.request.JsonRequest;

String baseUrl = "http://github.com/"
String path = "v1/json"
String listenerId = "001";//Unique Id

//1. Instantiate request object
Request request = new JsonRequest(application, baseUrl, path); 

//Or for file download
Request request = new FileRequest(application, baseUrl, path); 

//2. Create a Listener to listen to response from server
DownloadListener listener = new DownloadListener() {
        @Override
        public void onCompleted(InputStream inputStream) {
            try {
                List<ResourceInfo> info = utility.readJsonStream(inputStream);

                if (info != null)
                    setValue(info);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Throwable e) {

        }
    };

//3. Subscribe to request
resource.subscribe(listener, listenerId);
```

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)
