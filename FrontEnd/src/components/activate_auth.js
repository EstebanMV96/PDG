import React, { Component } from 'react';
import logo from '../logo4.svg';
import '../App.css';

//------IMAGES------------//
import escudo1 from '../images/escudos1.svg'
import escudo2 from '../images/escudos2.svg'
import escudo3 from '../images/escudos3.svg'

//------COMPONENTS--------//
import ReactModal from 'react-modal';
import ModalContent from './modal_content';

import {connect} from 'react-redux';

//------ACTIONS----------//
import {getQR,registryUser} from '../actions';


class App extends Component {

  constructor () {
    super();
    this.state = {
      showModal: false
    };
    
    this.handleOpenModal = this.handleOpenModal.bind(this);
    this.handleCloseModal = this.handleCloseModal.bind(this);
    this.handleCancelModal = this.handleCancelModal.bind(this);
  }



  handleOpenModal () {
    const id = this.props.match.params.id; 
    this.props.registryUser(id, (response) => {
      // if(response.status===200){
        // }
      });
      this.obtenerCodigo(id);
  }

    obtenerCodigo(id) {
        this.props.getQR(id);
        this.setState({ showModal: true });
    }

  handleCancelModal(){
    this.setState({ showModal: false });    
  }
  
  handleCloseModal () {
    this.setState({ showModal: false });
    const id = this.props.match.params.id;
    const url = this.props.match.params.url;   
    
    // const id = this.props.location.state.id.id;    
    this.props.history.push(
      {
        pathname: '/login',
        state: { id , url}
      }
    );
  }

  renderModal(){
    const qrbar = this.props.api.qr;
    return(
      <ReactModal 
        isOpen={this.state.showModal}
        contentLabel="Minimal Modal Example"
      >
      <ModalContent qr={qrbar} handleModalCancelar={this.handleCancelModal} handleModalAceptar={this.handleCloseModal} />
      </ReactModal>
    );


  }

  render() {
    return (
      <div className="App">
        <div className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <br />
          <button  className="activar btn-activar btn-lg" onClick={this.handleOpenModal}>Activar</button>
          
          {this.renderModal()}
        </div>
        <div id="table_container">
        <table className="imagenes table-responsive" >
          <tbody>
          <tr>
            <td>
              <img src={escudo1} alt="Not load" className="media-object" />
            </td>
            <td>
              <img src={escudo2} alt="Not load" className="media-object" />
            </td>
            <td>
              <img src={escudo3} alt="Not load" className="media-object" />
            </td>
          </tr>
          
          </tbody>
        </table>
        </div>
      </div>
    );
  }
}

function mapStateToProps(state){
  return {api:state.api};
}

export default connect(mapStateToProps,{getQR,registryUser})(App);
