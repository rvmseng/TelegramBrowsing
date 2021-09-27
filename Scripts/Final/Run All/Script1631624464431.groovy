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

import groovy.ui.SystemOutputInterceptor
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

int app_index = Math.ceil(Math.random() * 10).toInteger() % GlobalVariable.G_InstanceCount

ArrayList<Integer> inactiveList = new ArrayList<Integer>()
HashMap<Integer,Long> delayList = new HashMap<Integer,Long>()

System.out.println("[info] starting...");
while (true) {
    GlobalVariable.counter = System.currentTimeMillis()

    if (isActive(app_index,inactiveList) && reachDelayTime(app_index,delayList)) {
		System.out.println("[info] open multiParallel app for brows");
		WebUI.callTestCase(findTestCase('multi_parallel/open multiParallel app'), [:], FailureHandling.STOP_ON_FAILURE)
		
		System.out.println("[info] select telegram app["+app_index+"] for brows");
		WebUI.callTestCase(findTestCase('multi_parallel/select telegram app'), [('index') : (app_index + 1).toString()], 
            FailureHandling.STOP_ON_FAILURE)

        rs = Mobile.verifyElementExist(findTestObject('Object Repository/telegram/start_message_btm'),GlobalVariable.G_Timeout,FailureHandling.CONTINUE_ON_FAILURE)

        if (rs) {
            inactiveList.add(app_index)
			System.out.println("\n [Warning] Telegram["+app_index+"] added to inactive list\n")
        } else {
			System.out.println("[info] brows telegram["+app_index+"] channels");
			int rs=(Integer)WebUI.callTestCase(findTestCase('telegram/brows telegram channels'), [:], FailureHandling.STOP_ON_FAILURE)
			if(rs==0) {
				continue
			}
			else if(rs==1) {
				long time=System.currentTimeSeconds()+(GlobalVariable.delayTime*60)
				delayList.put(app_index, time)
				System.out.println("\n [Warning] Telegram["+app_index+"] added to delay list\n")
			}
			System.out.println("[info] close multiParallel");
            WebUI.callTestCase(findTestCase('common/close app'), [:], FailureHandling.STOP_ON_FAILURE)
			
			System.out.println("[info] open Root Explorer app");
            WebUI.callTestCase(findTestCase('RS/open RS app'), [:], FailureHandling.STOP_ON_FAILURE)

			System.out.println("[info] copy file");
            WebUI.callTestCase(findTestCase('RS/copy file'), [('instance_index') : app_index.toString(), ('instance_folder') : GlobalVariable.folderNamePrefix + 
                    (app_index + GlobalVariable.step).toString()], FailureHandling.STOP_ON_FAILURE)

			System.out.println("[info] close Root Explorer app");
			WebUI.callTestCase(findTestCase('common/close app'), [:], FailureHandling.STOP_ON_FAILURE)

			System.out.println("[info] open multiParallel app for clear catche");
			WebUI.callTestCase(findTestCase('multi_parallel/open multiParallel app'), [:], FailureHandling.STOP_ON_FAILURE)

			System.out.println("[info] select telegram["+app_index+"] app for clear catche");
            WebUI.callTestCase(findTestCase('multi_parallel/select telegram app'), [('index') : (app_index + 1).toString()], 
                FailureHandling.STOP_ON_FAILURE)

			System.out.println("[info] do clear cache");
			WebUI.callTestCase(findTestCase('telegram/clear cache'), [:], FailureHandling.STOP_ON_FAILURE)
			
			System.out.println("[info] close multiParallel app after clear cache");
			WebUI.callTestCase(findTestCase('common/close app'), [:], FailureHandling.STOP_ON_FAILURE)
        }
    }
    else {
		Mobile.delay(10);
	}
	
    app_index++
    app_index = (app_index % GlobalVariable.G_InstanceCount)
	System.out.println("[info] end of all action");
}

def getData(ResultSet rs) {
    str = ''

    while (rs.next()) {
        str = rs.getString(1)
    }
    
    return str
}

boolean isActive(int app,ArrayList<Integer> inactiveList) {
	for(i in inactiveList) {
		if(i==app) {
			System.out.println("\n [Warning] Telegram["+app+"] is not active\n")
			return false
		}
	}
	
	return true;
}

boolean reachDelayTime(int app,HashMap<Integer,Long> delayList) {
	long ct=System.currentTimeSeconds()
	long t=(delayList.get(app)!=null ? delayList.get(app) : 0)
	
	if(ct>=t) {
		return true
	}
	
	System.out.println("\n [Warning] Telegram["+app+"] does not reach delay time\n")
	return false
}