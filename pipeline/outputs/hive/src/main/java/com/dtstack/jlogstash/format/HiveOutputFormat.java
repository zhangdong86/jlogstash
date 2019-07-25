/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dtstack.jlogstash.format;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.RecordWriter;
import org.apache.hadoop.mapred.Reporter;
import org.apache.htrace.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;


/**
 * @author haisi
 */
public abstract class HiveOutputFormat implements  OutputFormat {

    protected static final String SP = "/";
    protected static final int NEWLINE = 10;
    protected Charset charset;
    protected String writeMode;
    protected boolean overwrite;
    protected String compress;
    protected List<String> columnNames;
    protected int columnSize;
    protected List<String> columnTypes;
    protected  String outputFilePath;
    protected  FileOutputFormat<?, ?> outputFormat;
    protected  JobConf jobConf;
    protected  Configuration conf;
    protected  Map<String, String> columnNameTypeMap;
    protected  Map<String, Integer> columnNameIndexMap;
    protected  RecordWriter recordWriter;
    protected String fileName;


    public static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure() {
        columnSize = this.columnNames.size();
        jobConf = new JobConf(conf);
    }

    @Override
    public abstract void writeRecord(Map<String,Object> row) throws IOException;

    @Override
    public void close() throws IOException {
        RecordWriter<?, ?> rw = this.recordWriter;
        if(rw != null) {
            rw.close(Reporter.NULL);
        }
    }
    
}
