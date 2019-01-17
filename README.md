# An Imgur image uploading service

# Prerequisite
* Ensure Maven and Java is installed.
* Ensure docker daemon is up and running.

## Steps to start the service
1. Clone this repo : 
```https://github.com/acp297/imgur_image_upload_service.git```

2. Navigate to the repository
```cd imgur_image_upload_service```

3. Start the service.
```sh run.sh```


##### POST REQUEST:
* URL  ```http://localhost:8080/v1/images/upload```

* BODY 
```json
      {"urls": 
           [
             "https://wallpaperbrowse.com/media/images/hd-wallpapers-1.jpg",
             "https://wallpaperbrowse.com/media/images/preview_vatna-glacier-icelend.jpg",
             "https://images.pexels.com/photos/946343/pexels-photo-946343.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940"
           ]
      }
```

##### GET REQUEST:
* URL ```http://localhost:8080/v1/images/```


##### GET REQUEST BY JOBID
* URL ```http://localhost:8080/v1/images/{JobID}```


