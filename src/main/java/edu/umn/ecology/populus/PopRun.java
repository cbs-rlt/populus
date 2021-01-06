/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus;

import edu.umn.ecology.populus.core.DesktopWindow;
import edu.umn.ecology.populus.fileio.Logging;

import javax.swing.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("edu.umn.ecology.populus")
public class PopRun implements ApplicationRunner {
    private static Logger LOG = LoggerFactory.getLogger(PopRun.class);
    final ResourceBundle res = ResourceBundle.getBundle("edu.umn.ecology.populus.core.Res");
    boolean packFrame = false;

    public static void main(String[] args) {
        LOG.info("Main called for Populus. About to do Spring initialization.");
        SpringApplicationBuilder sab = new SpringApplicationBuilder(PopRun.class);
        sab.headless(false).build().run(args);
    }

    @Override
    public void run(ApplicationArguments args) {
        final List nonOptionArgs = args.getNonOptionArgs();
        final String[] sourceArgs = args.getSourceArgs();
        final Set optionNames = args.getOptionNames();
        LOG.info("Spring initialized, now starting Populus");

        nonOptionArgs.forEach(nonOption -> LOG.info("## Non Option Args : "+nonOption));
        optionNames.forEach(option -> LOG.info("## Option Names    : "+option));
        Arrays.stream(sourceArgs).forEach(srcArgs ->LOG.info("## Source Args     : "+srcArgs));

        edu.umn.ecology.populus.fileio.Logging.log(res.getString("Populus_Starting_") + " on " + System.getProperty("os.name"));
        String buildTime = "<Unable to find build time>";
        try {
            //Read timestamp.txt from current directory.
            InputStream is = PopRun.class.getResourceAsStream("timestamp.txt");
            InputStreamReader isr = new InputStreamReader(is);
            java.io.BufferedReader ri = new java.io.BufferedReader(isr);
            buildTime = ri.readLine();
        } catch (Exception whateverHappensInMinnesotaStaysInMinnesota) {
            Logging.log(whateverHappensInMinnesotaStaysInMinnesota, "Cannot get build timestamp");
        }
        Logging.log("Populus built " + buildTime + "\n");
        try {
            Logging.log("Using " + UIManager.getSystemLookAndFeelClassName());
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            Object[] localUIDefaults = new String[]{
                    "StyledRadioButtonUI", "edu.umn.ecology.populus.visual.StyledRadioButtonUI",
                    "BracketUI", "edu.umn.ecology.populus.visual.BracketUI"
            };
            UIManager.getLookAndFeelDefaults().putDefaults(localUIDefaults);
            new DesktopWindow();
        } catch (Exception e) {
            Logging.log(e, "Failed to initialize Populus");
        }
    }

    @Bean
    public ApplicationRunner applicationRunner(ApplicationContext ctx) {
        return args -> {
            LOG.info("Beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                LOG.info(beanName);
            }
        };
    }

}

