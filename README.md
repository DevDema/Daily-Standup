# Daily Standup app

> :warning: **WORK IN PROGRESS**: This project is still a WIP. You can look at the code, but don't expect anything finished!

Daily Standup is an application which will allow to manage order of people talking during professional recurring meetings.\
Do you ever get confused about the order of people in recurring meetings, and all of you end up talking over each other?\
Stop this with Daily Standup app.

## Setup

This project doesn't need any particular setup process.\
It is enough to open the project in Android Studio (or any IDE you like) and get into the code!\

## Views

This application shows a mock meeting you could be in.
You can _Call next_ to the subsequent person that has to talk, _Skip_ them if they are missing for today's meeting, or look at their _Tasks_ in this sprint! (**WIP**)
Meeting screens:\
![Loading screen](screenshots/loading.png)
![Two notifications](screenshots/notifications.png)

## Project structure

This project follows MVVM clean architecture principles and structure. It contains:

- A database layer (RoomDB)
- A domain layer
- Presentation layer

This project uses _Hilt_ to inject necessary data as long as viewModels.\
This project uses _Android navigation library_ to define the navigation of the application.\
This project uses _safeArgs_ to pass data between navigation destinations.

## Data Logic

The project provides, as a mock reference, a prepopulated database.\
In future, you will be able to define your own meetings and people.