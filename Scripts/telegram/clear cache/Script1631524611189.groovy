import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

Mobile.waitForElementPresent(findTestObject('telegram/clear cache/left_menu'), GlobalVariable.G_LongTimeOut)

Mobile.tap(findTestObject('telegram/clear cache/left_menu'), GlobalVariable.G_Timeout)

rs = Mobile.waitForElementPresent(findTestObject('telegram/clear cache/settings_menu'), GlobalVariable.G_Timeout)

if (rs) {
    Mobile.tap(findTestObject('telegram/clear cache/settings_menu'), GlobalVariable.G_Timeout)
	
	Mobile.scrollToText("Cache Settings")

    rs = Mobile.waitForElementPresent(findTestObject('telegram/clear cache/cache_settings_option'), GlobalVariable.G_Timeout)

    if (rs) {
        Mobile.tap(findTestObject('telegram/clear cache/cache_settings_option'), GlobalVariable.G_Timeout)

        rs = Mobile.waitForElementPresent(findTestObject('telegram/clear cache/local_database'), GlobalVariable.G_Timeout)

        if (rs) {
            Mobile.tap(findTestObject('telegram/clear cache/local_database'), GlobalVariable.G_Timeout)

            rs = Mobile.waitForElementPresent(findTestObject('telegram/clear cache/clear_btm'), GlobalVariable.G_Timeout)

            if (rs) {
                Mobile.tap(findTestObject('telegram/clear cache/clear_btm'), GlobalVariable.G_Timeout)

                Mobile.tap(findTestObject('telegram/back_btm_in_header'), GlobalVariable.G_Timeout)
            }
        }
        
        Mobile.tap(findTestObject('telegram/back_btm_in_header'), GlobalVariable.G_Timeout)
    }
}
