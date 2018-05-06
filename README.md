# FlickerSample

This sample app uses the Flickr image search API and shows the results in a 3-column scrollable view.

## Architecture

Project is splitted into feature packages, currently the app has a single feature (query), each feature is splitted into 3 layers:
- Data Layer
- Domain Layer
- Presentation Layer

### Data Layer

All data needed for the feature comes throught this layer. Here we use the repositry design pattern so outter layers doesn't know where the data is coming from and repositories decide wither data is loaded from network or file or something else.

in this sample we only have a network implementation (FlickerRepository)

### Domain Layer

Which contains the business logic related to the feature, for this example is deciding wither loading new searches or loading next page for the same search.

### Presentation Layer

Responsible of presenting data to user and here it is implemented with 3 compnents:
- Presenter
- View Model
- View

Presenter takes state from Domain layer and converts it into a viewmodel that can be rendered by the View.

Note: Syncronizing to UI thread happens in the View itself.

## Things to improve
- Adding Error handlilng for netwrok failures and adding retrials.
- Threading model isn't very effecent, currently search is done on a single thread executor and photo loading is done serially using AsyncTasks.
- Adding proper prioritization for image loading. currently when flinging tasks are cancelled for views that are out of screen. having priority queue for requests would improve that.
