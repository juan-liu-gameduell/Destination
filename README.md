# Destination
### Introduction

Destination is an Android project developed with latest techniques including CoordinatorLayout, RecyclerView, Material Design, etc. It is developed for learning and practicing purpose. 
It depends on google places API to search cities and places in details.

The project is majorly developed in 10 days (4th, Sep to 14th Sep, see commit history), during which I learned a lot. There should still be some bugs or defeats, but I'm keeping optimizing it. 

<br><br>

### Features

Please install [app/app-release.apk](https://github.com/janeeliu/Destination/blob/master/app/app-release.apk) for testing.

<br>

Users can search cities for its photos, and points of interest, such as parks, churches, libraries, historical buildings, etc. The results of points of interests includes
photos and detail information. Clicking a place to show it in Google Map. User could also add a city into his favorite cities.

Here are some screenshots:

<img src="https://github.com/janeeliu/Destination/blob/master/art/main_home.jpeg" width="240">
<img src="https://github.com/janeeliu/Destination/blob/master/art/main_drawer.jpeg" width="240">
<img src="https://github.com/janeeliu/Destination/blob/master/art/city_detail.jpeg" width="240">

<br><br>

### Technical challenges:

* Handling orientation changes. Data is kept in SavedInstanceState and retained in onCreate.
* Switching Fragments through DrawerLayout. Fragments are kept while switching. Manually handled "back" action to go back to MainFragment from FavoriteFragment, because FragmentTransaction.addToBackStack() conflicts with manually switching. 
* CoordinatorLayout. UI effect adjustment.
* Using sub-threads to handle heavy tasks.
* Avoiding memory leak.

<br><br>

### Android Technologies:

* Activity: 
MainActivity and CityDetailActivity. MainActivity is the home page, it shows recommended cities and favorite cities in two Fragments which can be switched through items in DrawerLayout. CityDetailActivity shows a photo gallery, and lists some points of interest nearby the city.
Orientation changing is elegantly handled.  
* Fragment:
MainFragment shows recommended cities and FavoriteFragment shows favorite cities. 
* Toolbar: 
An independent Toolbar in MainActivity and a compound Toolbar inside CoordinatorLayout in CityDetailActivity.
* DrawerLayout: 
As a navigation of switching MainFragment and FavoriteFragment.
* RecyclerView: 
It shows grids in MainFragment and FavoriteFragment. Another RecyclerView shows a List in CityDetailActivity.
* CardView
* CoordinatorLayout: 
In CityDetailActivity, CoordinatorLayout provides cool scroll effect, I'm a fan of it.
* ViewPager: 
In CityDetailActivity, ViewPager is used to show a series of photo of target city.
* DialogFragment: 
DialogFragment is used to show network error message;
* Service: 
When the app is launched, a Service will be started to download recommended cities' data or read them from cache. As the data of recommended cities rarely change, they were cached in file system.
* SharedPreferences: 
To store favorite cities' ids.
* Intents: 
Explicit and implicit Intents.
* Handler: 
In the city details activity, it uses a handler to automatically swipe between photos in the gallery.
* BroadcastReceiver: 
It is used to receive messages from service.
* AsyncTask: 
It is used to read cities' data from files and download cities' data and places of interest's data from internet

<br><br>

### Third-part libraries:
* Retrofit: 
It is used to do the network tasks.
* Piccaso: 
To download, cache and show images.
* Google Api: 
[Place AutoComplete](https://developers.google.com/places/web-service/autocomplete) to search cities. [Place Details](https://developers.google.com/places/web-service/details) to fetch city details.

<br><br>

### TODO

* Refactoring network part
Now rewriting network part with [RxJava](https://github.com/ReactiveX/RxJava).
* Learn to modularise structure with [MVP](http://code.tutsplus.com/tutorials/an-introduction-to-model-view-presenter-on-android--cms-26162).
* Add animations







