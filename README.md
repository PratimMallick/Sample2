# Sample2

#This is a small example of how to fetch Data by calling a REST API using Volley library using MVP architecture.

#Following are the modules used:
#1. Views
#	MainActivity - Uses a recyclerView to show data which it fetches from the Presenter Layer
#	DetailActivity - Shows the detailed information of each product
#1.1 Adapter
#	ProductAdapter - act as the adapter for recyclerView
#
#2. Presenter
#	MainPresenter - Acts as the presenter layer for MainActivity
#	DetailPresenter - Acts as the presenter layer for DetailActivity

#3. Models
#	The POJOs which is used for deserializing of data using gson

#4. Helper
#	VolleyRequestHelper - used by presenter Layer to communicate with server and fetch data

