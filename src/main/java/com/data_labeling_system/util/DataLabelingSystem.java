package com.data_labeling_system.util;

import java.io.IOException;

import com.data_labeling_system.model.Dataset;

public class DataLabelingSystem {

    private Dataset dataset;
    private IOManager ioManager;
    private UserManager userManager;
    private InstanceTagger instanceTagger;

    public DataLabelingSystem() {
        this.ioManager = new IOManager();
        this.userManager = new UserManager();
        this.instanceTagger = new InstanceTagger();

    }

    public void startSystem() throws IOException {
        String configJson = this.ioManager.readInputFile("config.json");
        String datasetJson = this.ioManager.readInputFile("input-2.json");
        dataset = new Dataset(datasetJson);
        userManager.createUsers(configJson);
        this.dataset.setUsers(userManager.getUsers());
        this.instanceTagger.setDataset(this.dataset);
        this.instanceTagger.setUsers(this.userManager.getUsers());
        this.instanceTagger.assignLabels();
        this.dataset = this.instanceTagger.getDataset();
        this.ioManager.printFinalDataset(this.dataset, "output.json");
    }
}