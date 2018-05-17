# Project Title
Blocket price recommendation system
One Paragraph of project description goes here
The following price recommendation system is able to provide users useful insight regarding a product that they would like to sell / buy in a single place. The system is based on the advertisements available on Blocket’s website. It fetches all the advertisements from www.blocket.se , indexes it in the local system and keep it ready for users to get useful information from it. This is done by typing the query entered by the user which describes the product, during their search operation. The users are provided with an option to select appropriate categories and their attributes (if any present, as seen on Blocket). The final information, based on the search query and the categories / attributes selected are displayed on the screen. The information presented to the user shows a range of prices for which a user could sell the product, like the minimum, maximum and average prices. 

### Prerequisites

Windows, Linux or Mac OS.

Prior to executing the application following software is needed:
Maven
Eclipse / IntelliJ or any editor capable of opening and editing the Maven project.
Elastic Search 6.2.4

What things you need to install the software and how to install them

Maven
Download Maven from https://maven.apache.org/download.cgi#
and instal it according to https://maven.apache.org/install.html 

IDE
Choose an editor of your preference, download and install it accoringly to the
product download and installation manuals.

Elastic Search 6.2.4
Download Elastic Search from https://www.elastic.co/downloads/elasticsearch and
based on your system install it according to that website. 
```
Install
Eclipse /IntelliJ or simmilar IDE.
Elastic Search 6.2.4 (https://www.elastic.co/downloads/elasticsearch)
```
## Running the application



*First go to a folder where the Elastic Search 6.2.4 is located and run bin/elasticsearch (or bin\elasticsearch.bat on Windows).

*Second, extract the code zip folder to some location of you computer.

*Then, open your IDE and click on File -> Open... and navigate to the ...\code\Craigslist-price-recommendations\MeavenProject\mave-price_rec because the pom.xml file is located there. After opening a project the editor will understant that it is a Maven project. In the project (inside the IDE) navigate to \src\main\java\com\price_recomendation and there there will be 4 files, i.e: Ad.java, Main.java, Crawler.java and ElasticSearch.java. Click on Main.java and run it through the IDE. The process of crawling the blocket and indexing the data should start. Approximately it should take about 30-50 minutes depending on the computer and Internet connection.

*Finnaly, in the code zip folder that is previously extracted a Blocket folder is located. Again, in your prefered IDE open a new project but this time navigate to ...\code\Craigslist-price-recommendations\Blocket where is the location of a new pom.xml because this is another Maven project. Inside the IDE navigate to \Blocket\src\ where you will find Main.java. Click on that file and the run it through the editor. After that, the UI of the application is activated.

## Built With

* [Elastic Search](https://www.elastic.co/) - Search Engine used
* [Maven](https://maven.apache.org/) - Dependency Management
* [IntelliJ](https://www.jetbrains.com/idea/) - Used to edit/compile and run the code
* [Eclipse](https://www.eclipse.org/) - Used to edit/compile and run the code

## Authors

* **Sandra Pico** - *sandrapo@kth.se*
* **Dusan Viktor Hrstic** - *hrstic@kth.se* 
* **Andreas Rosenback** - *rosenba@kth.se*
* **Suhas Iyengar** - *suhass@kth.se*




