const chokidar = require("chokidar");
const { exec } = require("child_process");

const watcher = chokidar.watch([
  "src/main/resources/fxml/**/*.fxml",
  "src/main/resources/styles/**/*.css",
  "src/main/java/**/*.java"
]);

console.log("Watching for file changes...");

watcher.on("change", (path) => {
  console.log(`File ${path} has been changed`);

  exec("mvn compile", (error, stdout, stderr) => {
    if (error) {
      console.error(`Error: ${error}`);
      return;
    }
    console.log(`Recompiled: ${stdout}`);
  });
});