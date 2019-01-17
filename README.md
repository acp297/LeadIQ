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
             "https://farm3.staticflickr.com/2879/11234651086_681b3c2c00_b_d.jpg",
             "https://farm4.staticflickr.com/3790/11244125445_3c2f32cd83_k_d.jpg"
           ]
      }
```

##### GET REQUEST:
* URL ```http://localhost:8080/v1/images/```


##### GET REQUEST BY JOBID
* URL ```http://localhost:8080/v1/images/{JobID}```


