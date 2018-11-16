import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { openFile, byteSize, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './logo-my-suffix.reducer';
import { ILogoMySuffix } from 'app/shared/model/logo-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ILogoMySuffixProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class LogoMySuffix extends React.Component<ILogoMySuffixProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { logoList, match } = this.props;
    return (
      <div>
        <h2 id="logo-my-suffix-heading">
          Logos
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp; Create new Logo
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Logo Id</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {logoList.map((logo, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${logo.id}`} color="link" size="sm">
                      {logo.id}
                    </Button>
                  </td>
                  <td>
                    {logo.logoId ? (
                      <div>
                        <a onClick={openFile(logo.logoIdContentType, logo.logoId)}>
                          <img src={`data:${logo.logoIdContentType};base64,${logo.logoId}`} style={{ maxHeight: '30px' }} />
                          &nbsp;
                        </a>
                        <span>
                          {logo.logoIdContentType}, {byteSize(logo.logoId)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${logo.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${logo.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${logo.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ logo }: IRootState) => ({
  logoList: logo.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(LogoMySuffix);
