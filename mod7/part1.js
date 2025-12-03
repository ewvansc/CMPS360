

const readline = require("readline");

const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout,
});

const answers = {};

rl.question("What is your name? ", (name) => {
  answers.name = name;

  rl.question("What is your favorite food? ", (food) => {
    answers.food = food;

    rl.question("On a scale of 1-10, how tired are you today? ", (tired) => {
      answers.tired = tired;

      console.log("\n--- Summary ---");
      console.log(`Hi ${answers.name}!`);
      console.log(`Your favorite food is: ${answers.food}.`);
      console.log(`Your tiredness level today is: ${answers.tired}/10.`);
      console.log("Thanks for answering my questions!");

      rl.close();
    });
  });
});
