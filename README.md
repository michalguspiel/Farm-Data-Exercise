# Farm-Data-Exercise

## Some instructions

In order to get an app running place the google-services.json file that I provided in email attachment into  Android app module root directory.
Credentials for administrator account are provided in file with instructions. Without it project won't compile. 

In another branch of this repository there's python file with code that I used in order to get provided farm datas on firebase server.
Some data was discarded because of the validation rules provided in the task. In order to test those validation rules I implemented small test suite.
In order to run this test download both repository branch, open cmd, change directore to that file and then type "python -m unittest firestore_import_test.py" without quotation marks.

Android tests can be run straight from Android studio.

## Description
 ### General description
I accomplished all of the features for Frontend and technically some for backend since I used Firebase cloud(I did CSV parse and validation, ).
I am quite satisfied of the final result since I had limited time and treated this as a hobby project with a bigger meaning(applying for an academy).

App fetches farms and farm data from firebase server and presents farms on the map and farm data as graph and table. Authentication feature is implemented and works perfectly, user can sign up, sign in, sign out. Signed in user can create farm by long clicking on the map. User which created the farm can add new data to the farm.

Due to limited amount of time I had to use two libraries which I found on github, 
If this would be my app to solve some real world problem I would propably try to implement this functionallity myself 
since those libraries had their limits. For example graph library provided horizontally scrollable graph which was not ideal solution in my opinion,
I worked around it by computing last visible point on the screen and then adjusting points to be visible only until that point( as a ratio % of full screen)
I wanted to have big calendar when selecting time period to see farm data from, however now that I see app there could be two different buttons to select that data
matter of taste I'd say.

 ### Decisions
I have chosen to develop this app with relatively new library jetpack compose,
I have chosen it because I really wanted to learn it for a few months already but didn't have time for it.
I work and study there's not a lot of time for hobby coding. However, now I had a little bit less work because of Covid-19 and I also had winter break from university
and this seemed like a perfect project to get my hands dirty with jetpack compose. 

MVVM seemed like a best match to work with compose. I decided to implement it with clean architecuture to make my code more testable and clean. 

Splash screen fetches data about farms and saves it in the room, therefore during the lifecycle of an app that data is downloaded only once. Good thing about it is 
that it limits data reads. Bad thing that map is not updated in live time, for example if someone created farm after You launched app, you won't see it.

 ### Problems
Because of usage of Maps SDK with jetpack compose however my app got a little bit more diffcult to test. I implemented E2E tests for feature_auth but testing 
feature_farmData was more difficult because of the problem i mentioned earlier. It's already saturday, day before deadline and I can't spend my whole day on making the tests work
however with bigger amount of time I would probably try to implement more tests for feature_farmData. 
 
 

## Features implemented
- Authentication
- Splash screen
- Map which shows farms
- Data Graph
- Data table
- Possibility to add new farms for authenticated user
- Possibility to add new farm data restricted for farm owner

## Technologies
- Firebase
- Room local database
- Jetpack compose
- Flows
- Kotlin corutines
- MVVM
- Clean architecture
- Coil
- Android Google Maps SDK
- boguszpawlowski.composecalendar (Calendar library)
- madrapps:plot (Graph library)
