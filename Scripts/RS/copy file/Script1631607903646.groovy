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

Mobile.tap(findTestObject('RS/first_tab'), GlobalVariable.G_Timeout)

Mobile.tap(findTestObject('RS/data_folder1'), GlobalVariable.G_Timeout)

Mobile.waitForElementPresent(findTestObject('RS/data_folder2'), GlobalVariable.G_Timeout)

Mobile.tap(findTestObject('RS/data_folder2'), GlobalVariable.G_Timeout)

Mobile.scrollToText('multi.parallel.dualspace.cloner', FailureHandling.STOP_ON_FAILURE)

Mobile.waitForElementPresent(findTestObject('RS/multi_parallel_folder'), GlobalVariable.G_Timeout)

Mobile.tap(findTestObject('RS/multi_parallel_folder'), GlobalVariable.G_Timeout)

Mobile.waitForElementPresent(findTestObject('RS/virtual_folder'), GlobalVariable.G_Timeout)

Mobile.tap(findTestObject('RS/virtual_folder'), GlobalVariable.G_Timeout)

Mobile.waitForElementPresent(findTestObject('RS/data_folder3'), GlobalVariable.G_Timeout)

Mobile.tap(findTestObject('RS/data_folder3'), GlobalVariable.G_Timeout)

Mobile.waitForElementPresent(findTestObject('RS/user_folder'), GlobalVariable.G_Timeout)

Mobile.tap(findTestObject('RS/user_folder'), GlobalVariable.G_Timeout)

Mobile.waitForElementPresent(findTestObject('RS/folder_x', [('index') : instance_index]), GlobalVariable.G_Timeout)

Mobile.tap(findTestObject('RS/folder_x', [('index') : instance_index]), GlobalVariable.G_Timeout)

Mobile.waitForElementPresent(findTestObject('RS/telegram_folder'), GlobalVariable.G_Timeout)

Mobile.tap(findTestObject('RS/telegram_folder'), GlobalVariable.G_Timeout)

Mobile.waitForElementPresent(findTestObject('RS/files_folder'), GlobalVariable.G_Timeout)

Mobile.tap(findTestObject('RS/files_folder'), GlobalVariable.G_Timeout)

Mobile.waitForElementPresent(findTestObject('RS/cache4.db_file'), GlobalVariable.G_Timeout)

Mobile.tapAndHold(findTestObject('RS/cache4.db_file'), 3, GlobalVariable.G_Timeout)

Mobile.waitForElementPresent(findTestObject('RS/copy_toolbar'), GlobalVariable.G_Timeout)

Mobile.tap(findTestObject('RS/copy_toolbar'), GlobalVariable.G_Timeout)

Mobile.tap(findTestObject('RS/second_tab'), GlobalVariable.G_Timeout)

Mobile.waitForElementPresent(findTestObject('RS/pictures_folder'), GlobalVariable.G_Timeout)

Mobile.tap(findTestObject('RS/pictures_folder'), GlobalVariable.G_Timeout)

Mobile.scrollToText(instance_folder)

Mobile.waitForElementPresent(findTestObject('RS/instance_x', [('instance_name') : instance_folder]), GlobalVariable.G_Timeout)

Mobile.tap(findTestObject('RS/instance_x', [('instance_name') : instance_folder]), GlobalVariable.G_Timeout)

Mobile.tap(findTestObject('RS/paste_btm'), GlobalVariable.G_Timeout)

Mobile.scrollToText("cache4.db")
Mobile.waitForElementPresent(findTestObject('RS/cache4.db_file'), GlobalVariable.G_Timeout)

Mobile.tapAndHold(findTestObject('RS/cache4.db_file'), 5, GlobalVariable.G_LongTimeOut)
Mobile.tap(findTestObject('RS/right_menu'), GlobalVariable.G_Timeout)

Mobile.waitForElementPresent(findTestObject('RS/rename_option'), GlobalVariable.G_Timeout)
Mobile.tap(findTestObject('RS/rename_option'), GlobalVariable.G_Timeout)

newName="cache4 ("+GlobalVariable.counter.toString()+").db"

Mobile.waitForElementPresent(findTestObject('RS/filename_txt'), GlobalVariable.G_Timeout)
Mobile.setText(findTestObject('RS/filename_txt'),newName, GlobalVariable.G_Timeout)

Mobile.tap(findTestObject('RS/ok_btm'), GlobalVariable.G_Timeout)
