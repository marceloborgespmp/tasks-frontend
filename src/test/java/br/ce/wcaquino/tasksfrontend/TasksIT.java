package br.ce.wcaquino.tasksfrontend;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class TasksIT {

    public WebDriver initApplication() throws MalformedURLException {
        //WebDriver driver = new ChromeDriver();
        Capabilities driverCap = DesiredCapabilities.chrome();
        WebDriver driver = new RemoteWebDriver(new URL(System.getProperty("selenium.grid.hub")), driverCap);
        driver.navigate().to(System.getProperty("app.baseuri")+"/tasks/");
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        return driver;
    }

    @Test
    public void deveIncluirTarefaComSucesso() throws MalformedURLException {

        WebDriver driver = initApplication();
        try {
            //clicar em Add Todo
            driver.findElement(By.id("addTodo")).click();

            //escrever descricao
            driver.findElement(By.id("task")).sendKeys("Teste via Selenium");

            //escrever data
            driver.findElement(By.id("duedate")).sendKeys("10/10/2021");

            //clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            //validar mensagem de sucesso
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Success!", message);
        } finally {
            //fechar browser
            driver.quit();
        }
    }

    @Test
    public void naoDeveIncluirTarefaSemDescricao() throws MalformedURLException {

        WebDriver driver = initApplication();
        try {
            //clicar em Add Todo
            driver.findElement(By.id("addTodo")).click();

            //escrever data
            driver.findElement(By.id("duedate")).sendKeys("10/10/2021");

            //clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            //validar mensagem de sucesso
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Fill the task description", message);
        } finally {
            //fechar browser
            driver.quit();
        }
    }


    @Test
    public void naoDeveIncluirTarefaSemData() throws MalformedURLException {

        WebDriver driver = initApplication();
        try {
            //clicar em Add Todo
            driver.findElement(By.id("addTodo")).click();

            //escrever descricao
            driver.findElement(By.id("task")).sendKeys("Teste via Selenium");

            //clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            //validar mensagem de sucesso
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Fill the due date", message);
        } finally {
            //fechar browser
            driver.quit();
        }
    }

    @Test
    public void naoDeveIncluirTarefaDataPassada() throws MalformedURLException {

        WebDriver driver = initApplication();
        try {
            //clicar em Add Todo
            driver.findElement(By.id("addTodo")).click();

            //escrever descricao
            driver.findElement(By.id("task")).sendKeys("Teste via Selenium");

            //escrever data
            driver.findElement(By.id("duedate")).sendKeys("10/10/2020");

            //clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            //validar mensagem de sucesso
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Due date must not be in past", message);
        } finally {
            //fechar browser
            driver.quit();
        }
    }

}