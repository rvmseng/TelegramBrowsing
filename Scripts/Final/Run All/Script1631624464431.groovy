import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import java.sql.ResultSet as ResultSet
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

int app_index = 0

while (true) {
	
	GlobalVariable.counter=System.currentTimeMillis() 
	
    WebUI.callTestCase(findTestCase('multi_parallel/open multiParallel app'), [:], FailureHandling.STOP_ON_FAILURE)

    WebUI.callTestCase(findTestCase('multi_parallel/select telegram app'), [('index') : (app_index+1).toString()], FailureHandling.STOP_ON_FAILURE)

    WebUI.callTestCase(findTestCase('telegram/brows telegram channels'), [:], FailureHandling.STOP_ON_FAILURE)

    WebUI.callTestCase(findTestCase('common/change db status'), [:], FailureHandling.STOP_ON_FAILURE)

    WebUI.callTestCase(findTestCase('common/close app'), [:], FailureHandling.STOP_ON_FAILURE)

    WebUI.callTestCase(findTestCase('RS/open RS app'), [:], FailureHandling.STOP_ON_FAILURE)

    //WebUI.callTestCase(findTestCase('RS/setup folders'), [:], FailureHandling.STOP_ON_FAILURE)

    WebUI.callTestCase(findTestCase('RS/copy file'), [('instance_index') : app_index.toString(), ('instance_folder') : GlobalVariable.folderNamePrefix + 
            (app_index + GlobalVariable.step).toString()], FailureHandling.STOP_ON_FAILURE)

    WebUI.callTestCase(findTestCase('common/close app'), [:], FailureHandling.STOP_ON_FAILURE)

    WebUI.callTestCase(findTestCase('multi_parallel/open multiParallel app'), [:], FailureHandling.STOP_ON_FAILURE)

    WebUI.callTestCase(findTestCase('multi_parallel/select telegram app'), [('index') : (app_index+1).toString()], FailureHandling.STOP_ON_FAILURE)

    WebUI.callTestCase(findTestCase('telegram/clear cache'), [:], FailureHandling.STOP_ON_FAILURE)

    app_index++
	app_index = app_index % (GlobalVariable.G_InstanceCount)
}

def getData(ResultSet rs) {
    str = ''

    while (rs.next()) {
        str = rs.getString(1)
    }
    
    return str
}
