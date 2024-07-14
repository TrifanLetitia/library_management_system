document.addEventListener('DOMContentLoaded', () => {
    const popUp = document.querySelector('.pop_up');
    const closeButton = document.querySelector('.close_btn');
    // Assuming you have a reopen button or area within the teaser part
    const reopenButton = document.querySelector('.reopen_btn'); 

    // Function to open the pop-up
    const openPopUp = () => {
        popUp.classList.add('pop_up--visible');
        reopenButton.style.display = 'none';
    };

    // Function to close the pop-up
    const closePopUp = () => {
        popUp.classList.remove('pop_up--visible');
        reopenButton.style.display = 'block';
    };

    closeButton.addEventListener('click', closePopUp);

    // Reopen action
    reopenButton.addEventListener('click', openPopUp);

    // Example trigger to initially open the pop-up
    setTimeout(openPopUp, 1000); // Opens the pop-up after a delay for demonstration
});

document.addEventListener('DOMContentLoaded', () => {
    const popUp = document.querySelector('.pop_up');
    const closeButton = document.querySelector('.close_btn');
    const reopenButton = document.querySelector('.reopen_btn'); // Get the reopen button

    // Optionally show the pop-up initially or through some other event
    setTimeout(() => {
        popUp.classList.add('pop_up--visible');
    }, 1000); // Delay for demonstration

    closeButton.addEventListener('click', function() {
        popUp.classList.remove('pop_up--visible');
    });

    reopenButton.addEventListener('click', function() {
        popUp.classList.add('pop_up--visible');
    });
});


const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
const container = document.getElementById('container');


signUpButton.addEventListener('click', () => {
    container.classList.add("right-panel-active");
});


signInButton.addEventListener('click', () => {
    container.classList.remove("right-panel-active");
});