import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import java.sql.ResultSet as ResultSet
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory as MobileDriverFactory
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import io.appium.java_client.AppiumDriver as AppiumDriver
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.WebElement as Keys

CustomKeywords.'com.database.MySQL.connectDB'(GlobalVariable.db_host, GlobalVariable.db_port, GlobalVariable.db_name, GlobalVariable.db_user, 
    GlobalVariable.db_password)

Mobile.waitForElementPresent(findTestObject('telegram/search_icon_in_header'), GlobalVariable.G_LongTimeOut)

int queue=0
boolean flag=true

for (def index : (1..GlobalVariable.G_ChannelCount)) {
    
	if(queue >= GlobalVariable.queue) {
		index = (GlobalVariable.G_ChannelCount + 1)
		flag=false;
		continue
	}
	
	result = CustomKeywords.'com.database.MySQL.executeQuery'('select t.id,t.username from telegram_channels t where t.`Status`=0 order by t.member_count desc limit 1')

    HashMap<String,String> map = getData(result)

    if (map.size()>0) {
        rs = Mobile.waitForElementPresent(findTestObject('telegram/search_icon_in_header'), GlobalVariable.G_Timeout)

        if (rs) {
            Mobile.tap(findTestObject('telegram/search_icon_in_header'), GlobalVariable.G_Timeout)
        }
        
		Map.Entry<String,String> entry=map.entrySet().iterator().next()
		String id=entry.getKey().toString()
		String cn=entry.getValue().toString()
		
        Mobile.sendKeys(findTestObject('telegram/search_txt_in_header'), cn, FailureHandling.CONTINUE_ON_FAILURE)

        Mobile.waitForElementPresent(findTestObject('telegram/search_first_result'), GlobalVariable.G_Timeout)

        AppiumDriver<?> driver = MobileDriverFactory.getDriver()

        List<WebElement> list = driver.findElementsByXPath('//*[@class = \'android.view.View\' and @resource-id = \'\' and (@text = \'\' or . = \'\')]')

        if (list.size() > 0) {
            c = list.size() > GlobalVariable.iteration ? GlobalVariable.iteration : list.size()

            boolean lock = true
			queue=0

            for (i = 0; i < c; i++) {
                list.get(i).click()

                rs = Mobile.waitForElementPresent(findTestObject('telegram/channel_icon_in_header'), GlobalVariable.G_Timeout)

                if (rs) {
                    Mobile.tap(findTestObject('telegram/channel_icon_in_header'), GlobalVariable.G_Timeout)

                    if (lock) {
                        query="update telegram_channels t set t.`Status`=2 where t.id="+id
                        CustomKeywords.'com.database.MySQL.execute'(query)
                        lock = false
                    }
                }
                
                rs = Mobile.waitForElementPresent(findTestObject('telegram/back_btm_in_header'), GlobalVariable.G_Timeout)

                if (rs) {
                    Mobile.tap(findTestObject('telegram/back_btm_in_header'), GlobalVariable.G_Timeout)
                }
                
                rs = Mobile.waitForElementPresent(findTestObject('telegram/back_btm_in_header'), GlobalVariable.G_Timeout)

                if (rs) {
                    Mobile.tap(findTestObject('telegram/back_btm_in_header'), GlobalVariable.G_Timeout)
                }
                
                if (i < (c - 1)) {
                    Mobile.tap(findTestObject('telegram/search_icon_in_header'), GlobalVariable.G_Timeout)

                    Mobile.sendKeys(findTestObject('telegram/search_txt_in_header'), cn, FailureHandling.CONTINUE_ON_FAILURE)

                    rs = Mobile.waitForElementPresent(findTestObject('telegram/search_first_result'), GlobalVariable.G_Timeout)

                    if (rs == false) {
                        i = (c + 1)
                    }
                }
            }
            
            if (lock) {
                query="update telegram_channels t set t.`Status`=4 where t.id="+id
                CustomKeywords.'com.database.MySQL.execute'(query)
            }
        } else {
			queue++
            query="update telegram_channels t set t.`Status`=4 where t.id="+id
            CustomKeywords.'com.database.MySQL.execute'(query)

            rs = Mobile.waitForElementPresent(findTestObject('telegram/close_btm_in_header'), GlobalVariable.G_Timeout)

            if (rs) {
                Mobile.tap(findTestObject('telegram/close_btm_in_header'), GlobalVariable.G_Timeout)
            }
            
            rs = Mobile.waitForElementPresent(findTestObject('telegram/back_btm_in_header'), GlobalVariable.G_Timeout)

            if (rs) {
                Mobile.tap(findTestObject('telegram/back_btm_in_header'), GlobalVariable.G_Timeout)
            }
        }
    } else {
        index = (GlobalVariable.G_ChannelCount + 1)
    }
}

CustomKeywords.'com.database.MySQL.closeDatabaseConnection'()
return flag;

synchronized HashMap<String,String> getData(ResultSet rs) {
	HashMap<String, String> map = new HashMap<String, String>()
    while (rs.next()) {
        id = rs.getString(1)
        username = rs.getString(2)
       
		 if (!(''.equals(id))) {
            map.put(id, username)
        }
    }
    
    if (map.size() > 0) {
		Map.Entry<String,String> entry=map.entrySet().iterator().next()
        query="update telegram_channels t set t.`Status`=1 where t.id="+entry.getKey().toString()
		CustomKeywords.'com.database.MySQL.execute'(query)
    }
    
    return map;
}