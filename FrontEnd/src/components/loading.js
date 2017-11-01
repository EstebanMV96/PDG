import React,{Component} from 'react';
import ReactLoading from 'react-loading';

import {connect} from 'react-redux';
import {verifyAuth} from '../actions';

class Loading extends Component {

    componentWillMount(){
        const id = this.props.match.params;        
        this.props.verifyAuth(id.id);
    }
    

    handle(){

    }

    render() {
        const id = this.props.match.params.id;
        const url = this.props.match.params.url;        
        var ver = this.props.api.verify;
        ver="NOK"
        if(ver){
            if(ver ==="OK"){
                this.props.history.push(
                    {
                        pathname: '/login',
                        state: { id:id, url:url}
                    }
                );
            }else{
                // this.props.history.push(
                //     {
                //         pathname: '/landing',
                //         state: { id:id, url:url }
                //     }
                // );
                window.location.assign(`https://${url}`);                
            }
        }

        return(
            <div className="loading">
                <table>
                    <tbody>
                        <tr>
                            <td />
                            <td>
                                <ReactLoading height={200} width={200} type="spinningBubbles" color="#0a67c3" />
                            </td>
                            <td />
                        </tr>
                    </tbody>
                </table>
            </div>
        );
    }
}

function mapStateToProps(state){
    return {api:state.api};
}

export default connect(mapStateToProps,{verifyAuth})(Loading);

