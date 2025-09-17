package TestRunner;

import io.cucumber.core.cli.Main;

public class TestRunner {
	public static void main(String[] args) throws Throwable{
        Main.main(new String[]{
            "--glue","Step", // the package which contains the glue classes
            "classpath:Features"
            ,"--tags","@CatalogConnector"
        }
        );
    }    

}
