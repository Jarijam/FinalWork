a3 <- function(){
  library(sqldf);
  # Read Log File
  data <- read.csv("finaldata.log",header = F,fileEncoding = "UTf-8")
  names(data) <- c('Y','M','D','HH','MM','SS','FLAME','TEMP','GAS','CRASH');
  # print(data);
  # Analysis - sql
  sdata <- sqldf(sprintf('SELECT MM, AVG(TEMP) AS TEMP, AVG(GAS) AS GAS FROM data GROUP BY MM'));
  print(sdata)

  # Return
  return (sdata);
}
a4 <- function(){
  library(sqldf);
  # Read Log File
  data <- read.csv("finaldata.log",header = F, fileEncoding = "UTF-8");
  names(data) <- c('Y','M','D','HH','MM','SS','FLAME','TEMP','GAS','CRASH');
  print(data);
  # Analysis - sql
  sdata <- sqldf(sprintf('SELECT Y,M,D,HH,MM,SS, AVG(GAS) AS GAS, AVG(TEMP) AS TEMP FROM data GROUP BY Y,M,D,HH,MM,SS'));
  print(sdata)
  return(sdata);
  
}

a6 <- function(){
  library(sqldf);
  # Read Log File
  data <- read.csv("finaldata.log",header = F, fileEncoding = "UTF-8");
  names(data) <- c('Y','M','D','HH','MM','SS','FLAME','TEMP','GAS','CRASH');
  print(data);
  # Analysis - sql
  sdata <- sqldf(sprintf('SELECT D, COUNT(GAS) FROM data WHERE GAS > 850 GROUP BY D ORDER BY D DESC LIMIT 1'));
  print(sdata)
  return(sdata);
  
  
}


a7 <- function(){
  library(sqldf);
  # Read Log File
  data <- read.csv("finaldata.log",header = F, fileEncoding = "UTF-8");
  names(data) <- c('Y','M','D','HH','MM','SS','FLAME','TEMP','GAS','CRASH');
  print(data);
  # Analysis - sql
  sdata <- sqldf(sprintf('SELECT D, COUNT(TEMP) FROM data WHERE TEMP > 30  GROUP BY D ORDER BY D DESC LIMIT 1'));
  print(sdata)
  return(sdata);
  
  
}





b1 <- function(){
  library(sqldf);
  # Read Log File
  data <- read.csv("finaldata.log",header = F, fileEncoding = "UTF-8");
  names(data) <- c('Y','M','D','HH','MM','SS','FLAME','TEMP','GAS','CRASH');
  print(data);
  # Analysis - sql
  sdata <- sqldf(sprintf('SELECT GAS,TEMP,FLAME FROM data GROUP BY D,HH,MM,SS ORDER BY D DESC,HH DESC,MM DESC,SS DESC LIMIT 1 '));
  print(sdata)
  return(sdata)
   

  
}
b2 <- function(){
  library(sqldf);
  # Read Log File
  data <- read.csv("finaldata.log",header = F, fileEncoding = "UTF-8");
  names(data) <- c('Y','M','D','HH','MM','SS','FLAME','TEMP','GAS','CRASH');
  print(data);
  # Analysis - sql
  sdata <- sqldf(sprintf('SELECT TEMP FROM data GROUP BY D,HH,MM,SS ORDER BY D DESC,HH DESC,MM DESC,SS DESC LIMIT 1 '));
  print(sdata)
  return(sdata)
  
  
  
}

b3 <- function(){
  library(sqldf);
  # Read Log File
  data <- read.csv("finalcoord.log",header = F, fileEncoding = "UTF-8");
  names(data) <- c('Y','M','D','HH','MM','SS', 'CX','CY');
  print(data);
  # Analysis - sql
  sdata <- sqldf(sprintf('SELECT MM,SS, CX, CY  FROM data GROUP BY MM,SS ODER BY MM DESC, SS'));
  print(sdata)
  return(sdata)
  
  
  
}
b4 <- function(){
  library(sqldf);
  # Read Log File
  data <- read.csv("finaldata.log",header = F,fileEncoding = "UTf-8")
  names(data) <- c('Y','M','D','HH','MM','SS','FLAME','TEMP','GAS','CRASH');
  # print(data);
  # Analysis - sql
  sdata <- sqldf(sprintf('SELECT MM,SS AVG(TEMP) AS TEMP, AVG(GAS) AS GAS FROM data GROUP BY MM, SS '));
  print(sdata)
  
  # Return
  return (sdata);
}

b5 <- function(){
  library(sqldf);
  # Read Log File
  data <- read.csv("finaldata.log",header = F, fileEncoding = "UTF-8");
  names(data) <- c('Y','M','D','HH','MM','SS','FLAME','TEMP','GAS','CRASH');
  #print(data);
  # Analysis - sql
  sdata <- sqldf(sprintf('SELECT MM,SS, TEMP ,GAS, FLAME FROM data GROUP BY D,HH,MM,SS ORDER BY D DESC,HH DESC,MM DESC,SS DESC LIMIT 1'));
  print(sdata)
  return(sdata);
  
}






