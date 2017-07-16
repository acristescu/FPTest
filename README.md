# FP Test

[![BuddyBuild](https://dashboard.buddybuild.com/api/statusImage?appID=596bdf2d1cd4900001c52386&branch=master&build=latest)](https://dashboard.buddybuild.com/apps/596bdf2d1cd4900001c52386/build/latest?branch=master)

Features:
* [LoginActivity](app/src/main/java/io/zenandroid/fptest/login/LoginActivity.java) and
[AccountProfileDetailsActivity](app/src/main/java/io/zenandroid/fptest/accountdetails/AccountProfileActivity.java)
implement the two main required screens
* Automatic login by saving the username and password. Yes, this is not secure
but this was the only way of ensuring that the password is displayed on
the profile screen (which is a direct part of the requirements). Alternative
would have been saving the token and user id
* Change the avatar by taking photo or choosing from gallery
* Limit the uploded image size to 1MB by doing a binary search for the
perfect ratio to scale down. This was a trade-off, discussed more in
ImageUtils.java
* Use Gravatar if no avatar is set. [GravatarUtils](app/src/main/java/io/zenandroid/fptest/util/GravatarUtils.java)
* Avatar is displayed in a circle
* Apply an inverse filter to image before uploading. This is done asynchronously
in [ImageProcessingService](app/src/main/java/io/zenandroid/fptest/service/ImageProcessingService.java)
by taking advantage of a few things from Picasso.
* MVP architecture (instead of VIPER which is very uncommon on Android)
* __Unit tests__ All presenters are unit tested with pure JUnit local tests
* __UI Tests__ A navigation test is performed with Espresso.
* __CI Integration__ Continuous integration is provided via BuddyBuild.
Note that the whole Unit and UI Tests are run on any commit. Please click
on the badge above to see the status of the tests on the latest build.
* Dagger2 injection
* Butterknife
* Retrofit + OKHttp + Otto (Eventbus alternative that plays nice with JUnit)
architecture that allows a clean and concise way to perform asynchronous
calls to a REST API. See diagram below or my [blog post](http://zenandroid.io/testable-and-robust-architecture-for-android-projects/)
for more info
* Two flavors for backend: either connected via the `prod` flavor or local
(no internet required) via the `mock` flavor. Make sure to check which
flavor you have selected in Android Studio as you may miss some of the code
(e.g. [UsersServiceModule.java](blob/master/app/src/prod/java/io/zenandroid/fptest/dagger/UsersServiceModule.java))
* mock online API http://demo7231530.mockable.io/users/1

> Please note that I have not used the Hungarian notation, as I consider
it unnecessary (and I'm not the only one). However, if the team requires
it, I am more than happy to oblige.

## Overview of architecture

Here is the diagram of the architecture proposed here:

![](https://cdn.rawgit.com/acristescu/GreenfieldTemplate/master/architecture.svg)

The flow of events and data:

1. The __Activity__ reacts to the user input by informing the __Presenter__ that data is required.
1. The __Presenter__ fires off the appropriate request in the service layer (and instructs the __Activity__ to display a busy indicator).
1. The __Service__ then issues the correct REST call to the __Retrofit__ layer (providing a callback).
1. The __Retrofit__ layer exchanges HTTP requests and responses with the Server and then calls either the `onSuccess` or `onFailure` method (as the case may be) on the provided __Callback__.
1. The __Callback__ posts the data or error on the __Event Bus__.
1. The __Presenter__ (being subscribed the relevant data and errors on the __Event Bus__) receives the data or error and issues the correct commands to the __Activity__ to update the UI (and dismiss the busy indicator).
1. The __Activity__ presents the user with the data or error message.
