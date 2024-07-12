<h1>Springboot service for Leisure centre</h1>
<p>It is a service for the public to sign up a membership for a fake leisure centre, Tony Leisure Centre. Also, the member can login the system and register the time slot for the facilities. The admin can remove the time slot and the system will notificate the members when they registered the removed time slot.</p>

<h2>Public APIs</h2>
The public APIs do not need the authorization. 
POST: baseUrl/public/auth/register: For the public to sign-up the membership
POST: baseUrl/public/auth/authenticate: For member and abmin to login the system
GET: baseUrl/public/section/2024-06-19: For public to fetch the time-slot detail.

<h3>Membership Sign-up</h3>
<h5>API:baseUrl/public/auth/register</h5>
<h5>Method: POST</h5>
<p>Request and response for sign-up membership sucessfully:</p>
<p>There is a token responsed for authorizing the admin and member APIs.</p>

![signup](https://github.com/user-attachments/assets/7597ec28-eafa-438b-a652-03e295ee4dd0)

<p>Validations for blank nessesary fields:</p>

![signup-feildsnotblank](https://github.com/user-attachments/assets/5cf0ebf1-55d6-45a9-a043-0af4c3e3a4b5)

<p>Validations for wrong email format:</p>

![signup-emilwrongformat](https://github.com/user-attachments/assets/82287613-cabc-445c-ace6-80a06904a663)

<p>Validation for signup with existed email</p>

![signupwithsameemail](https://github.com/user-attachments/assets/80e98b99-6654-43b5-9264-8931a76a14e6)

<h3>Login</h3>
<h5>API:baseUrl/public/auth/authenticate</h5>
<h5>Method: POST</h5>

![image](https://github.com/user-attachments/assets/cd73df85-dfd7-45fd-b17e-4a10824638bf)

<p>Validations for login API</p>

![image](https://github.com/user-attachments/assets/9af3788e-12e7-4eff-9bd9-0aca860d9aff)

<h3>Get the Section of the facilities on practicular date</h3>
<h5>API:baseUrl/public/section/2024-06-19</h5>
<h5>Method: GET</h5>
<p>Get a list of sections of facilities on particular date.</p>

![image](https://github.com/user-attachments/assets/1ad0af1f-b891-4c97-a6c3-055a2c04269b)


<p>If the admin did not create the sections on the date serched, an error meassage will be response.</p> 

![getSectionWithoutOnThatDate](https://github.com/user-attachments/assets/69e78d59-547f-4359-918b-ac7c18abd7c8)

<h2>Admin and Member APIs</h2>
<p>The reponsibility of admin is to create the sections to book and cancel it if necessary. After cancelling the sections, a notification will be created automatically to notice the member who book the cancelled sections. </p>
<p>On the other hand, member can access the member APIs to register the sections and get the notification.</p>
<p>Only the user with ADMIN role can access Admin APIs. </p>

![image](https://github.com/user-attachments/assets/73a2f405-34a8-4aa7-a9f8-a04bc3bf2825)

<p>Otherwise, the 403 will be responsed.</p>

![image](https://github.com/user-attachments/assets/67708a65-3f5a-4db0-b4aa-5ccea6fd6185)

<h3>API for Admin to create sections</h3>
<h5>API:baseUrl/admin/section/create</h5>
<h5>Method: POST</h5>
<p>In the requset body, a date is provided. Then a list of sections will be created. </p>

![createSectionByDate](https://github.com/user-attachments/assets/2c0383d4-391a-4531-a8ee-a11fa9342408)

![image](https://github.com/user-attachments/assets/eec83c45-b511-452a-9796-3abb1732098f)


<p>If the sections has been created, a error massage will be responsed.</p>

![image](https://github.com/user-attachments/assets/2c8e9a03-b5ea-4eb2-b991-a598f3d3355a)

<h3>API for Member to register section by section ID</h3>
<h5>API:baseUrl/admin/section/create</h5>
<h5>Method: POST</h5>
<p>Providing the section ID to register the section.</p>

![image](https://github.com/user-attachments/assets/90f9fbfa-480c-41a7-913f-3d487b9596c7)

<p>The status of the section will be changed to Occupied. Then other member cannot register it agein.</p>

![image](https://github.com/user-attachments/assets/618544a8-b4cb-4c09-a42e-82b9d87234ce)

<p>Validation: A error message will be sent. If the section register agein.</p>

![image](https://github.com/user-attachments/assets/8a186740-36f7-4679-a467-b2f19b56f564)

<p>Validation: If wrong section ID is provided, an error message will be responsed. </p>

![image](https://github.com/user-attachments/assets/5ba28019-e954-45f0-9ff4-d10ec3d43c53)

<h3>API for Member to get their register section</h3>
<h5>API:baseUrl/member/section</h5>
<h5>Method: GET</h5>
<p>Get the sections by the info of token. </p>

![image](https://github.com/user-attachments/assets/0de6b2bc-5ba5-49e4-8379-db8530ce1fe8)


<h3>API for Admin to cancel the sections</h3>
<h5>API:baseUrl/admin/section/cancel</h5>
<h5>Method: POST</h5>
<p>Cancel the section by section ID. </p>

![image](https://github.com/user-attachments/assets/fa7d33e5-4b68-4153-9768-d4598f2e3f40)

![image](https://github.com/user-attachments/assets/e90902dd-3761-4a72-a092-c38381597e6c)

<p>A message for member who registered the section is created automatically.</p>

![image](https://github.com/user-attachments/assets/469d15c4-b7ef-47d0-a41e-e88e3c9b5371)

<h3>API for Member to get the notifications</h3>
<h5>API:baseUrl/member/notification</h5>
<h5>Method: GET</h5>

<p>The notifications are also gotten by the info of token.</p>

![image](https://github.com/user-attachments/assets/93d968a7-505f-4962-8b87-534ddfa029fb)







