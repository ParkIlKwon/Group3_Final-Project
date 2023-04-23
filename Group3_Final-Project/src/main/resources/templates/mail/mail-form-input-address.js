// document.querySelector('.mail-address').addEventListener('keydown', function(event) {
//     alert("32 , 13");
//     if (event.keyCode === 32 || event.keyCode === 13) { // Check for space (32) or enter (13) key
//         alert("32 , 13. 13");
//         event.preventDefault(); // Prevent default behavior of space or enter key
//         alert("32 , 13");
//         const inputValue = event.target.value.trim(); // Get the input value and trim whitespace
//         if (inputValue !== '') { // Check if input value is not empty
//             const testDiv = document.querySelector('.test'); // Get the div.test element
//             const testChildDiv = document.createElement('div'); // Create a new div element for div.testChild
//             testChildDiv.className = 'testChild'; // Set the class name of the new div element
//             testChildDiv.textContent = inputValue; // Set the text content of the new div element to the input value
//             testDiv.appendChild(testChildDiv); // Append the new div element to div.test
//             event.target.value = ''; // Clear the input value
//         }
//     }
// });

window.onload = function () {
    document.querySelector('.mail-address').addEventListener('keydown', () => {
        alert('test');
    });

}