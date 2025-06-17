package com.ejemplo.testng; 

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files; // Para Files.copy
import java.nio.file.StandardCopyOption; // Para StandardCopyOption.REPLACE_EXISTING

public class ExtentReporterListener implements ITestListener {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        String reportPath = System.getProperty("user.dir") + "/test-output/ExtentReport.html";
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // Información del sistema en el reporte
        extent.setSystemInfo("Host Name", "localhost");
        extent.setSystemInfo("Environment", "QA"); // Puedes obtener esto de ConfigReader
        extent.setSystemInfo("User Name", "Denji616");
        extent.setSystemInfo("Browser", "Chrome/Firefox"); // Adaptar según lo que uses
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush(); // Guarda el reporte final
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(), result.getMethod().getDescription());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().log(Status.FAIL, "Test Failed");
        test.get().fail(result.getThrowable()); // Muestra la excepción en el reporte

        // Tomar captura de pantalla en caso de fallo
        try {
            WebDriver driver = (WebDriver) result.getTestContext().getAttribute("WebDriver"); // Obtener el driver del contexto
            if (driver != null) {
                String screenshotPath = takeScreenshot(driver, result.getMethod().getMethodName());
                test.get().addScreenCaptureFromPath(screenshotPath, "Failed Screenshot");
            } else {
                test.get().log(Status.WARNING, "WebDriver instance was null, could not take screenshot.");
            }
        } catch (IOException | NullPointerException e) {
            test.get().log(Status.WARNING, "Failed to take screenshot: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP, "Test Skipped");
        test.get().skip(result.getThrowable());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // No implementado para este caso
    }

    // Método para tomar captura de pantalla
    public String takeScreenshot(WebDriver driver, String screenshotName) throws IOException {
        // Asegúrate de que la carpeta de capturas exista
        String screenshotsDir = System.getProperty("user.dir") + "/test-output/screenshots/";
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String destinationPath = screenshotsDir + screenshotName + "_" + System.currentTimeMillis() + ".png";
        File destinationFile = new File(destinationPath);

        // Asegúrate de que el directorio de destino exista
        destinationFile.getParentFile().mkdirs();

        Files.copy(srcFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return destinationPath;
    }
}