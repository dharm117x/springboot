# b2b_site
----------------
Please follow the steps for B2B modulegen custom in Hybris 6.0

Step 1: Navigate to the /installer directory. install.bat -r b2c_acc for b2b custom

step 2- Navigate to the /platform directory.

Step 3 - Set your ant environment by entering the following command: setantenv.bat

Step 4 - Run the ant modulegen command 
ant modulegen -Dinput.module=accelerator -Dinput.name=dkshop -Dinput.package=com.dkshop -Dinput.template=production
ant extgen -Dinput.name=dkshopocc -Dinput.package=com.dkshop.occ -Dinput.template=yocc
ant extgen -Dinput.name=dkshopintegrations -Dinput.package=com.dkshop.integrations -Dinput.template=yempty
          

ant extgen -Dinput.name=nokiabackoffice -Dinput.package=com.nokia -Dinput.template=ybackoffice
ant extgen -Dinput.name=nokiawebservices -Dinput.package=com.nokia -Dinput.template=ycommercewebservices


if you want to change training name then change the extgen/project.properties in plateform folder

Step - 5 After running the ant modulegen command, open the localextensions.xml file, which is located in /hybris/config.

Project setup in windows with soft link:
============================================


remove or comment these extensions -yacceleratorcockpits,yacceleratorinitialdata, yacceleratorfulfilmentprocess, yacceleratorstorefront

add these extensions - trainingcore, trainingfacades, trainingstorefront, traininginitialdata, trainingtest, trainingcockpits, trainingfulfilmentprocess

Add the following B2B Accelerator extensions to the localextensions.xml file: 

powertoolsstore, b2bcommercebackoffice, b2badmincockpit, b2bacceleratoraddon, commerceorgaddon

Step- 6 Install the B2B Accelerator AddOns on the training storefront by running the following ant commands from the /platform directory:

ant addoninstall -Daddonnames="b2bacceleratoraddon" -DaddonStorefront.yacceleratorstorefront="dkshopstorefront"
ant addoninstall -Daddonnames="commerceorgaddon" -DaddonStorefront.yacceleratorstorefront="dkshopstorefront"
ant addoninstall -Daddonnames="assistedservicestorefront" -DaddonStorefront.yacceleratorstorefront="dkshopstorefront"
ant addoninstall -Daddonnames=cmsoccaddon -DaddonStorefront.ycommercewebservices=nokiawebservices


ant addoninstall -Daddonnames=smarteditaddon -DaddonStorefront.yacceleratorstorefront="dkshopstorefront"
npm install -g grunt-cli
npm install grunt-force-task --save-dev


Added secure login and user registration:
-------------------------------------------
ant addoninstall -Daddonnames="secureportaladdon" -DaddonStorefront.yacceleratorstorefront="dkshopstorefront"
ant all and system update
enbale website secure and regsitration flag by true


if used b2bocc then uninstall:
ant addonuninstall -Daddonnames=b2boccaddon -DaddonStorefront.ycommercewebservices=commercewebservices


Addon uninstall:
---------------------
ant addonuninstall -Daddonnames=smarteditaddon -DaddonStorefront.yacceleratorstorefront="dkshopstorefront"


Custom ADDON:
-----------------
ant extgen -Dinput.template=yaddon -Dinput.name=myextension -Dinput.package=com.myapp.myextension


Step-6.1: Update storfront spring-filter-config.xml file change sitechannel b2c to b2b

	<alias name="b2bAcceleratorSiteChannels" alias="acceleratorSiteChannels"/>
	<util:set id="b2bAcceleratorSiteChannels" value-type="de.hybris.platform.commerceservices.enums.SiteChannel">
		<ref bean="SiteChannel.B2B"/>
	</util:set>


Step- 7 Add the following properties to /hybris/config/local.properties:

website.dkshopUS.http=http://localhost:9001/dkshopstorefront 
website.dkshopUS.https=https://localhost:9002/dkshopstorefront


Step - 8 Build and initialize Hybris Commerce by running the following ant command from the /platform directory:

licence.bat -temp CPS_SQL

ant initialize

Step -9 Start the Hybris server by running the following command from the /platform directory:

hybrisserver.bat

Step -10 B2B Url for running

https://localhost:9002/dkshopstorefront/?site=dkshopUS

ant stopSolrServer

ant startSolrServer

https://localhost:8983/solr
solrserver/server123

ant updatesystem

====================================================
OCC data
=======================
INSERT_UPDATE OAuthClientDetails;clientId[unique=true]    ;resourceIds       ;scope        ;authorizedGrantTypes                                            ;authorities             ;clientSecret    ;registeredRedirectUri
                                ;client-side              ;hybris            ;basic        ;implicit,client_credentials                                     ;ROLE_CLIENT             ;secret          ;http://localhost:9001/authorizationserver/oauth2_implicit_callback;
                                ;mobile_android           ;hybris            ;basic        ;authorization_code,refresh_token,password,client_credentials    ;ROLE_CLIENT             ;secret          ;http://localhost:9001/authorizationserver/oauth2_callback;


curl -k -d "client_id=mobile_android&client_secret=secret&grant_type=client_credentials" -X POST https://localhost:9002/authorizationserver/oauth/token

================================
HYbris git setup windows
==============================
Admin console:
D:\>cd D:\projects\hybris6_b2c\hybris

mklink /D custom D:\projects\hybris11-b2b\b2b_site\core-customize\hybris\bin\custom

mklink /D config D:\projects\hybris11-b2b\b2b_site\core-customize\hybris\config

