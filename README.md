# project documentation  
in this document I attempt to present the project by presenting the technology, the architecture, and the logic used to develop this project.

I used the MVVM architecture wishes it to be based on the principle of clean architecture.

Presentation layer: the user interaction interface that displays the information to the user

Domain layer: This layer reduces the task complexity or the shared task between viewModels

Data: This layer provides the data to the ui layer

DTO Each layer have here owen data model

Single source of truth principale 

Tools :
* retrofit to ensure the communication with server
* room : to create local data base
* hilt : dependency Injection
* Flow & channel : to transfer the data between different layer
* navigation component: to ensure the navigation between fragments
* liveData to present the data from viewModel to the view
* compose : The detail view was impemented by the jetpack compose with the ui state proncipale 
* test android (local data base using hilt)
* retrofit and Ktor libraray to ensure the communication with server side
  
Dependency Injection to ensure the independence in the project i use the dependency injection design pattern .
to manage the dependency Injection i used the HILT library

