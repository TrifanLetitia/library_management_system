console.log(localStorage.getItem('userDetails'));

const addedBooks = [];

document.addEventListener("click", (e) => {
    if (e.target.closest('.add-to-card-btn')) {
        const button = e.target.closest('button');
        const itemElement = button.closest('.categ-prod-item');
        const imgSrc = itemElement.querySelector('.lazy').src;
        const title = itemElement.querySelector('.item-title h2').textContent.trim();
        const itemsList = document.querySelector(".items.list");
        const emptyText = document.querySelector(".empty-text");
        const cartPopup = document.getElementById('cart-popup');
        const id = itemElement.querySelector('.book-id').textContent;

        const noEx = parseInt(itemElement.querySelector('.ex_disponibile').textContent); // Obținem numărul de exemplare disponibile

        if (noEx <= 0) {
            alert('Ne pare rău, nu mai sunt exemplare disponibile pentru acest produs.');
            return;
        }

        if (addedBooks[id]) {
            alert('Puteți împrumuta un singur exemplar!');
            return;
        }

        if (Object.keys(addedBooks).length >= 5) {
            cartPopup.style.display = 'block';
            setTimeout(() => {
                cartPopup.style.display = 'none';
            }, 2000);
            return;
        }

        addedBooks[id] = true;

        const newItem = document.createElement('li');
        newItem.classList.add('item');

        const contentWrapper = document.createElement('div');
        contentWrapper.classList.add('item-content');

        const imageElement = document.createElement('img');
        imageElement.src = imgSrc;
        imageElement.width = 60;

        const titleParagraph = document.createElement('p');
        titleParagraph.textContent = title;
        titleParagraph.classList.add('title-large');

        const removeButton = document.createElement('button');
        removeButton.textContent = 'X';
        removeButton.classList.add('remove-item');
        removeButton.addEventListener('click', () => {
            newItem.remove();
            delete addedBooks[id];
            if (itemsList.children.length === 0) {
                emptyText.style.display = 'block';
            }
        });

        contentWrapper.appendChild(imageElement);
        contentWrapper.appendChild(titleParagraph);
        newItem.appendChild(contentWrapper);
        newItem.appendChild(removeButton);
        itemsList.appendChild(newItem);
        emptyText.style.display = 'none';

        window.scrollTo({
            top: 0,
            behavior: 'smooth'
        });
    }
});

document.querySelector(".empty-cart").addEventListener("click", () => {
    const itemsList = document.querySelector(".items.list");
    while (itemsList.firstChild) {
        itemsList.removeChild(itemsList.firstChild);
    }
    document.querySelector(".empty-text").style.display = 'block';
    for (let id in addedBooks) {
        delete addedBooks[id];
    }
});


document.addEventListener('DOMContentLoaded', function () {
    fetchBooks();
});

var allBooks = []

async function fetchBooks() {
    const apiURL = 'http://localhost:8080/carti';
    try {
        const response = await fetch(apiURL);
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        const books = await response.json();
        allBooks = books
        displayBooks(books);
    } catch (error) {
        console.error('Could not fetch books:', error);
    }
}

var noEx = 0;

async function getNoOfExemplareDisponibile(id) {
    const apiURL = `http://localhost:8080/carti/${id}/no`;
    try {
        const response = await fetch(apiURL);
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        const number = await response.json();
        noEx = number
    } catch (error) {
        console.error('Could not fetch books:', error);
    }
}

function displayBooks(books) {
    const booksContainer = document.getElementById('categ-prod-list');
    var no_aventura = 0;
    var no_psiho = 0;
    var no_politiste = 0;
    var no_dragoste = 0;
    var no_thriller = 0;
    var no_poezie = 0;

    books.forEach(async book => {
        const listItem = document.createElement('li');
        listItem.className = 'categ-prod-item';
        if (book.category == "Aventura") {
            no_aventura = no_aventura + 1;
        }
        if (book.category == "Psihologie") {
            no_psiho = no_psiho + 1;
        }
        if (book.category == "Politiste") {
            no_politiste = no_politiste + 1;
        }
        if (book.category == "Dragoste") {
            no_dragoste = no_dragoste + 1;
        }
        if (book.category == "Thriller") {
            no_thriller = no_thriller + 1;
        }
        if (book.category == "Poezie") {
            no_poezie = no_poezie + 1;
        }
        await getNoOfExemplareDisponibile(book.id);

        listItem.innerHTML = `
            <div class="pr-history-item">
                <span class="book-id" id="book-id" style="display: none;">${book.id}</span>
                <img class="lazy" width="120" height="185" src="${book.src}" alt="Poza produsului ${book.title} - ${book.author}">
                <div class="item-title">
                    <h2 class="pr-title-categ-pg">${book.title} - ${book.author}</h2>
                    <span ></span>
                </div>
                <div class="review review-slider normal-rew-stars">
                  
                </div>
                <div class="item-price">
                    <div class="price-discount-containerx">
                        <p class="price-reduced">Exemplare disponibile: <span class="ex_disponibile">${noEx}</span></p>
                    </div>
                </div>
                <div class="add-to-card-btn adauga-in-cos-roz-AB adauga-in-cos-roz">
                    <button>Adauga in cos
                        <img class="slider-btn-add-cart-icon adauga-in-cos-roz-AB-icon" src="https://cdn4.libris.ro/resurse/img/static/adauga-in-cos-img-roz.svg" alt="cart icon">
                    </button>
                </div>
            </div>`;

        booksContainer.appendChild(listItem);
    });

    document.getElementById("politiste").innerHTML = '(' + no_politiste + ')';
    document.getElementById("aventura").innerHTML = '(' + no_aventura + ')';
    document.getElementById("dragoste").innerHTML = '(' + no_dragoste + ')';
    document.getElementById("psihologie").innerHTML = '(' + no_psiho + ')';
    document.getElementById("thriller").innerHTML = '(' + no_thriller + ')';
    document.getElementById("poezie").innerHTML = '(' + no_poezie + ')';
}

function imprumutaCarti() {
    const itemsList = document.querySelector(".items.list");
    if (itemsList.children.length === 0) {
        alert('Coșul este gol!');
        return;
    }
    else {
        const userDetails = localStorage.getItem('userDetails');
        const obj = JSON.parse(userDetails);

        var id = obj.id;
        const today = new Date();
        const day = String(today.getDate()).padStart(2, '0');
        const month = String(today.getMonth() + 1).padStart(2, '0');
        const year = today.getFullYear();
        var date = day + '/' + month + '/' + year;
        const bookIds = Object.keys(addedBooks);
        console.log(bookIds)

        var borrowedBooks = {
            "id_user": id,
            "dataImprumut": date,
            "dataRestituire": "-",
            "exemplarImprumutatRequest": {
                "id_imprumut:": null,
                "id_carti": bookIds
            }
        }

        console.log(borrowedBooks)
        fetch('http://localhost:8080/imprumuturi', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(borrowedBooks)
    })
        .then(response => response.json())
        .then(data => {
            console.log('Succes:', data);
            setTimeout(() => {
                window.location.reload();
            }, 1000); // Delay de 1000ms (1 secundă)
        })
        .catch((error) => {
            console.error('Eroare:', error);
        });
    }
}

function resetDetails(){
    localStorage.removeItem('userDetails');
}
