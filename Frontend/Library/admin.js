document.addEventListener('DOMContentLoaded', function () {
    fetchReturnedBooks();
});

let loanList = [[]]

async function fetchReturnedBooks() {
    const userDetails = localStorage.getItem('userDetails');
    const obj = JSON.parse(userDetails);

    const apiURL = `http://localhost:8080/exemplare/returnate`;
    try {
        const response = await fetch(apiURL);
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        const books = await response.json();
        displayReturnedBooks(books)
    } catch (error) {
        console.error('Could not fetch books:', error);
    }
}

console.log(localStorage.getItem('userDetails'))

console.log(loanList)

function displayReturnedBooks(books){
    const container = document.getElementById('booksDisplay');
        container.innerHTML = '';
        books.forEach(book => {
            const loanDiv = document.createElement('div');
            loanDiv.className = 'imprumut';
            loanDiv.innerHTML = `
            <strong>ID Exemplar: ${book.id}</strong>
            <select id="status-${book.id}">
                <option value="disponibila">disponibila</option>
                <option value="pierduta">pierduta</option>
            </select>
            <button onclick="updateStatus('${book.id}', document.getElementById('status-${book.id}').value)">Modifică</button>`;
            container.appendChild(loanDiv);
        });
}

async function updateStatus(bookId, status){
    if(status == 'disponibila'){
        const apiURL = `http://localhost:8080/exemplare/${bookId}/updateStatus2`;
        try {
            const response = await fetch(apiURL, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
            });
            console.log(status)
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            const responseData = await response.json();
            console.log(`Book ID: ${bookId} returned successfully`, responseData);
        } catch (error) {
            console.error(`Could not return book ID: ${bookId}`, error);
        }
    }
    else if(status == 'pierduta'){
        const apiURL = `http://localhost:8080/exemplare/${bookId}/updateStatus3`;
        try {
            const response = await fetch(apiURL, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
            });
            console.log(status)
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            const responseData = await response.json();
            console.log(`Book ID: ${bookId} returned successfully`, responseData);
        } catch (error) {
            console.error(`Could not return book ID: ${bookId}`, error);
        }
    }

    setTimeout(() => {
        window.location.reload();
    }, 100);
}

function resetDetails(){
    localStorage.removeItem('userDetails');
}