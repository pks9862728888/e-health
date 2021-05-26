# e-health:
A sample e-health app

## Enumerations to be used while sending data from frontend form fields (CASE SENSITIVE)
**Gender Type enum:** 
```
    MALE,
    FEMALE,
    OTHERS
```
**Account Type enum:** 
```
    USER,
    HOSPITAL,
    PHYSICIAN,
    LABORATORY,
    PHARMACY,
    HOSPITAL_STAFF,
    PHYSICIAN_STAFF,
    LABORATORY_STAFF,
    PHARMACY_STAFF
```
**KYC Document Type enum:**
```
    AADHAR,
    PASSPORT,
    PAN,
    DRIVING_LICENSE;
```
## Backend API endpoints:
### Error Response:
This json format is consistent throughout the API, only the json content changes based on different kind of errors.<br><br>
**Content-type:** "application/json"<br>
**Format:**
```
{
  "httpStatusCode": 403,
  "httpStatus": "FORBIDDEN",
  "reason": "FORBIDDEN",
  "message": "YOU NEED TO LOGIN TO ACCESS THIS RESOURCE.",
  "timestamp": "24-05-2021 23:02:50"
}
```
## Registration endpoints:
### User Registration endpoint:
**Endpoint:** _/api/v1/user/sign-up_<br>
**Accepts:** "multipart/form-data"<br>
**Authentication required:** false<br>
**Request Method:** POST<br>
**Request Payload format:**
```
{
  "user_credentials": "{
      "username": "user1",
      "email": "email@gmail.com",
      "phone": "",
      "password": "toor",
      "account_type": "USER"
    }",
  "user_details": "{
      "first_name": "First Name User1",
      "last_name": "Last Name User1",
      "gender": "MALE",
      "date_of_birth": "12-12-1998"
    }",
  "document_type": "AADHAR",
  "id_front": <Multipart File>,
  "id_back": <Multipart File>
}
```
**Success Response format:**
```
{
    "httpStatusCode": 201,
    "httpStatus": "CREATED",
    "reason": "CREATED",
    "message": "ACCOUNT CREATION SUCCESSFUL! PLEASE ACTIVATE YOUR ACCOUNT BY CLICKING ON THE LINK MAILED TO YOU. AFTER THAT PLEASE WAIT TILL OUR BACKEND TEAM VERIFIES YOUR KYC DETAILS.",
    "timestamp": "24-05-2021 22:44:49"
}
```
**Error response codes:** 400<br>
**Acceptable file types:** .pdf, .jpeg, .png<br>
**Max file upload size:** 30 Mb<br>

***
### Physician Registration endpoint
**Endpoint:** _/api/v1/physician/sign-up_<br>
**Accepts:** "multipart/form-data"<br>
**Authentication required:** false<br>
**Request Method:** POST<br>

**Request Payload format:**
```
{
  "user_credentials": "{
      "username": "user1",
      "email": "email@gmail.com",
      "phone": "",
      "password": "toor",
      "account_type": "USER"
    }",
  "user_details": "{
      "first_name": "First Name User1",
      "last_name": "Last Name User1",
      "gender": "MALE",
      "date_of_birth": "12-12-1998"
    }",
  "physician_details": "{
      "fees": 12000.0,
      "currency": "INR",
      "years_of_experience": 1
    }"
  "document_type": "AADHAR",
  "id_front": <Multipart File>,
  "id_back": <Multipart File>
}
```
**Success Response format:**
```
{
    "httpStatusCode": 201,
    "httpStatus": "CREATED",
    "reason": "CREATED",
    "message": "ACCOUNT CREATION SUCCESSFUL! PLEASE ACTIVATE YOUR ACCOUNT BY CLICKING ON THE LINK MAILED TO YOU. AFTER THAT PLEASE WAIT TILL OUR BACKEND TEAM VERIFIES YOUR KYC DETAILS.",
    "timestamp": "24-05-2021 22:44:49"
}
```
**Error response codes:** 400<br>
**Acceptable file types:** .pdf, .jpeg, .png<br>
**Max file upload size:** 30 Mb<br>
