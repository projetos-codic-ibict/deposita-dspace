/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.scripts;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;
import org.dspace.core.Context;
import org.dspace.identifier.DOIIdentifierProvider;
import org.dspace.identifier.doi.DOIOrganiser;
import org.dspace.scripts.configuration.DOIOrganiserUIConfiguration;
import org.dspace.utils.DSpace;

public class DOIOrganiserUI extends DSpaceRunnable<DOIOrganiserUIConfiguration> {

    private String[] argsParaCLI;

    @Override
    public void setup() throws ParseException {
        // Pega as opções selecionadas na interface Angular e remonta em um Array 
        // idêntico ao que o terminal geraria.
        List<String> argList = new ArrayList<>();
        
        for (Option opt : commandLine.getOptions()) {
            if (opt.getOpt() != null) {
                argList.add("-" + opt.getOpt());
            } else {
                argList.add("--" + opt.getLongOpt());
            }
            // Se a opção tem um valor (ex: o número do DOI digitado)
            if (opt.getValue() != null) {
                argList.add(opt.getValue());
            }
        }
        
        this.argsParaCLI = argList.toArray(new String[0]);
    }

    @Override
    public void internalRun() throws Exception {
        handler.logInfo("Iniciando orquestração de DOIs via Interface Web...");
        
        Context context = new Context();
        context.turnOffAuthorisationSystem(); // Roda com permissão de sistema
        
        try {
            // Instancia o provider e a sua classe DOIOrganiser exatamente como no main()
            DOIIdentifierProvider provider = new DSpace().getSingletonService(DOIIdentifierProvider.class);
            DOIOrganiser organiser = new DOIOrganiser(context, provider);
            
            // Chama a lógica original do DSpace repassando os comandos da UI
            DOIOrganiser.runCLI(context, organiser, this.argsParaCLI);
            
            context.complete();
            handler.logInfo("Processo do DOI finalizado!");
            
        } catch (Exception e) {
            context.abort();
            handler.logError("Erro ao processar DOIs: " + e.getMessage());
            throw e;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public DOIOrganiserUIConfiguration getScriptConfiguration() {
        return new DOIOrganiserUIConfiguration();
    }
}