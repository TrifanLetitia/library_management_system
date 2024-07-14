const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
const container = document.getElementById('container');


signUpButton.addEventListener('click', () => {
    container.classList.add("right-panel-active");
});


signInButton.addEventListener('click', () => {
    container.classList.remove("right-panel-active");
});
async function signUp() {
    var formData = {
        name: document.getElementById('name').value,
        password: document.getElementById('password').value,
        cnp: document.getElementById('cnp').value,
        adress: document.getElementById('adress').value,
        phone: document.getElementById('phone').value
    };
    console.log(formData)

    fetch('http://localhost:8080/users', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    })
        .then(response => response.json())
        .then(data => {
            console.log('Succes:', data);
        })
        .catch((error) => {
            console.error('Eroare:', error);
        });
}

async function signIn() {
    var formData = {
        cnp: document.getElementById('signin_cnp').value,
        password: document.getElementById('signin_password').value,
    };

    try {
        const response = await fetch('http://localhost:8080/users/authenticate', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        });

        if (!response.ok) {
            throw new Error('Authentication failed');
        }
        const userDetails = await response.json();
        console.log('User details:', userDetails);

        localStorage.setItem('userDetails', JSON.stringify(userDetails));
        const userDetailsString = localStorage.getItem('userDetails');

        if (userDetailsString) {
            const userDetails = JSON.parse(userDetailsString);
            console.log('Detalii user preluate:', userDetails);
        }
        if(userDetails.roles[0].name == "ROLE_ABONAT"){
            const carti = window.location.origin + '/Library/carti.html';
            window.location.href = carti;
        }
        else if(userDetails.roles[0].name == 'ROLE_BIBLIOTECAR')
        {
            const carti = window.location.origin + '/Library/admin.html';
            window.location.href = carti;
        }
    } catch (error) {
        console.error('Error:', error.message);
    }
}