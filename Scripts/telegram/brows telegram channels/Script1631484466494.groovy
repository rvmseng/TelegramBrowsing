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

for (def index : (1..GlobalVariable.G_ChannelCount)) {
    result = CustomKeywords.'com.database.MySQL.executeQuery'(GlobalVariable.fetch_query)

    cn = getData(result)

    if (!(''.equals(cn))) {
        rs = Mobile.waitForElementPresent(findTestObject('telegram/search_icon_in_header'), GlobalVariable.G_Timeout)

        if (rs) {
            Mobile.tap(findTestObject('telegram/search_icon_in_header'), GlobalVariable.G_Timeout)
        }
        
        Mobile.sendKeys(findTestObject('telegram/search_txt_in_header'), cn, FailureHandling.CONTINUE_ON_FAILURE)

        Mobile.waitForElementPresent(findTestObject('telegram/search_first_result'), GlobalVariable.G_Timeout)

        AppiumDriver<?> driver = MobileDriverFactory.getDriver()

        List<WebElement> list = driver.findElementsByXPath('//*[@class = \'android.view.View\' and @resource-id = \'\' and (@text = \'\' or . = \'\')]')

        if (list.size() > 0) {
            c = list.size() > GlobalVariable.iteration ? GlobalVariable.iteration : list.size()

            lock = true

            for (i = 0; i < c; i++) {
                list.get(i).click()

                rs = Mobile.waitForElementPresent(findTestObject('telegram/channel_icon_in_header'), GlobalVariable.G_Timeout)

                if (rs) {
                    Mobile.tap(findTestObject('telegram/channel_icon_in_header'), GlobalVariable.G_Timeout)

                    if (lock) {
                        tmp = GlobalVariable.update_query
						tmp = tmp.replaceFirst('1', '2')
						tmp = tmp.replaceFirst('un', cn)
                        CustomKeywords.'com.database.MySQL.execute'(tmp)
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
                tmp = GlobalVariable.update_query
				tmp = tmp.replaceFirst('1', '0')
				tmp = tmp.replaceFirst('un', cn)           
                tmp = (tmp + ' and t.`Status`=1')

                CustomKeywords.'com.database.MySQL.execute'(tmp)
            }
        } else {
            tmp = GlobalVariable.update_query
            tmp = tmp.replaceFirst('1', '4')
			tmp = tmp.replaceFirst('un', cn)
			CustomKeywords.'com.database.MySQL.execute'(tmp)
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

def getData(ResultSet rs) {
    str = ''

    while (rs.next()) {
        str = rs.getString(1)
    }
    
    if (!(''.equals(str))) {
        tmp = GlobalVariable.update_query

        tmp = tmp.replace('un', str)

        CustomKeywords.'com.database.MySQL.execute'(tmp)
    }
    
    return str
}