const chokidar = require("chokidar");
const { exec } = require("child_process");

const APP_PATH = "src/main/java/com/example/App.java";

const watcher = chokidar.watch([
  "src/main/resources/**/*.{fxml,css}",
  "src/main/java/**/*.java"
], {
  persistent: true,
  ignored: APP_PATH
});

console.log("watching for changes...");

watcher.on("change", (path) => {
  console.log(`file ${path} has been changed`);
  
  // run maven compile instead of just touching the file
  exec("mvn compile", (error) => {
    if (error) {
      console.error(`error compiling: ${error}`);
      return;
    }
    console.log("recompiling...");
    
    // touch App.java to trigger reload
    exec(`touch ${APP_PATH}`, (error) => {
      if (error) {
        console.error(`error touching App.java: ${error}`);
      }
    });
  });
});