package org.dspace.scripts.configuration;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.dspace.scripts.DOIOrganiserUI;

public class DOIOrganiserUIConfiguration extends ScriptConfiguration<DOIOrganiserUI> {

    // Variável exigida pela classe pai
    private Class<DOIOrganiserUI> dspaceRunnableClass;

    // Métodos abstratos obrigatórios para a injeção do Spring
    @Override
    public Class<DOIOrganiserUI> getDspaceRunnableClass() {
        return dspaceRunnableClass;
    }

    @Override
    public void setDspaceRunnableClass(Class<DOIOrganiserUI> dspaceRunnableClass) {
        this.dspaceRunnableClass = dspaceRunnableClass;
    }

    @Override
    public Options getOptions() {
        Options options = new Options();
        
        // Ações em massa (aparecerão como opções clicáveis na UI)
        options.addOption("r", "register-all", false, "Registrar online todos os DOIs na fila");
        options.addOption("s", "reserve-all", false, "Reservar online todos os DOIs na fila");
        options.addOption("u", "update-all", false, "Atualizar metadados de todos os DOIs na fila");
        options.addOption("d", "delete-all", false, "Deletar online todos os DOIs na fila");
        
        // Ações individuais (aparecerão como campos de texto para digitar o DOI/UUID)
        options.addOption(Option.builder().longOpt("register-doi").hasArg().argName("DOI|ItemID|handle")
                .desc("Registrar um identificador específico").build());
        options.addOption(Option.builder().longOpt("reserve-doi").hasArg().argName("DOI|ItemID|handle")
                .desc("Reservar um identificador específico").build());
        options.addOption(Option.builder().longOpt("update-doi").hasArg().argName("DOI|ItemID|handle")
                .desc("Atualizar um identificador específico").build());
        options.addOption(Option.builder().longOpt("delete-doi").hasArg().argName("DOI|ItemID|handle")
                .desc("Deletar um identificador específico").build());
        
        return options;
    }
}