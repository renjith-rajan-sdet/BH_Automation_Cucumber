**Run guide for the automation framework**
********************************************

**System requirements**

* Windows 10/11
* JDK 21
* Mozilla Firefox (This is needed to open Allure reort after running tests)

**Firefox settings that must be done for opening allure report (to disable strict origin policy)**

1) Open firefox.
2) On the address bar - type : about:config
3) Click on the button : Accept the risk and continue
4) Search the preference name : 'security.fileuri.strict_origin_policy' and turn the setting to 'False'

**Snaps**

<img width="815" alt="image" src="https://github.com/user-attachments/assets/45c9f961-91ce-4bd0-896e-0f71c286c095" />
<img width="748" alt="image" src="https://github.com/user-attachments/assets/19b26d07-5e97-47a9-8d93-5545f3291283" />
<img width="953" alt="image" src="https://github.com/user-attachments/assets/97fc8cb1-af16-4081-bf8b-bfbf5dafbac7" />

**Running test cases**

1) Go to the project folder and start cmd mode.
2) Type the run command and press ENTER : gradlew.bat clean test -Dcucumber.filter.tags=@SanityTests -PBrowser=Chrome -PEnv=QA generateReport -Ptest.parallel=true

   (The above command is going to run the 2 tests automated).
4) Once done, the allure reports will be generated in the folder : allure-report. Open the file : allure-report/index.html in firefox to view the allure report.
5) Cucumber reports will be generated in the path : target/cucumber-reports and can be opened by opening the file (use any browser) : target/cucumber-reports/Html.html

**snaps**

<img width="959" alt="image" src="https://github.com/user-attachments/assets/c6294419-8e3f-42eb-85ad-6b385d8dd6e8" />
<img width="950" alt="image" src="https://github.com/user-attachments/assets/71c33fb0-7c64-440f-bce6-bf3a5fa47753" />
<img width="953" alt="image" src="https://github.com/user-attachments/assets/bd2c2eb8-281e-4e9c-990f-9f32b9e7a766" />
<img width="957" alt="image" src="https://github.com/user-attachments/assets/f8a286e1-9664-4d61-9a89-58da033eee41" />
<img width="937" alt="image" src="https://github.com/user-attachments/assets/cbd44427-6a4e-454b-adf0-63bea2875f93" />
<img width="947" alt="image" src="https://github.com/user-attachments/assets/99019e14-c8e8-424a-9be7-d22ff4fc7e26" />
<img width="945" alt="image" src="https://github.com/user-attachments/assets/c5652953-294c-4566-af33-70749fcfebed" />











