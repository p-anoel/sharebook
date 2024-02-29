import Book from './Book'
import React from 'react'
import axios from 'axios';
import { useNavigate } from "react-router-dom";
import './MyBooks.scss'
import SimpleModal from './SimpleModal'

const MyBooks = () => {

  const [myBooks, setMyBooks] = React.useState([]);
  const [showModal, setShowModal] = React.useState(false)

  const fetchBooks = () => {
    axios.get('/books').then(response => {
      setMyBooks(response.data)
    })
  }

  const history = useNavigate();

  React.useEffect(() => {
    fetchBooks();
  }, [])

  const deleteMyBook = (bookId) => {
    axios.delete(`/books/${bookId}`).then(() => {
      fetchBooks();
    }).catch(error => {
      setShowModal(true)
    })
  }

  const handleCloseModal = () => {
    setShowModal(false)
  }

  return (
    <>
      <div className='container'>
        <h1>Mes livres</h1>
        <div className="list-container">
          {myBooks.length === 0 ? "Vous n'avez pas déclaré de livres" : null}
          {myBooks.map((book) => (<div className="mybook-container" key={book.id}>
          <Book title={book.title} category={book.category.label}></Book>
          <div className="container-buttons">
            <button className="btn btn-primary btn-sm" onClick={() => history(`/addBook/${book.id}`)}>Modifier</button>
            <button className="btn btn-primary btn-sm" onClick={() => deleteMyBook(book.id)}>Supprimer</button>
          </div>
          </div>))}
        </div>
          <button className="btn btn-primary btn-sm" onClick={() => history(`/addBook`)}>Nouveau Livre</button>
      </div>
      <SimpleModal
        title={"Supression de livre impossible"}
        bodyTxt={"Livre en cours d'emprunt"}
        handleCloseModal={handleCloseModal}
        showModal={showModal}
      ></SimpleModal>
    </>
    )
  }

  export default MyBooks;