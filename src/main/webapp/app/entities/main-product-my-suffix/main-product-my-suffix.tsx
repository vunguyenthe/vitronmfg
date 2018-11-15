import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { openFile, byteSize, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './main-product-my-suffix.reducer';
import { IMainProductMySuffix } from 'app/shared/model/main-product-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMainProductMySuffixProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class MainProductMySuffix extends React.Component<IMainProductMySuffixProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { mainProductList, match } = this.props;
    return (
      <div>
        <h2 id="main-product-my-suffix-heading">
          Main Products
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp; Create new Main Product
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Description</th>
                <th>Photo</th>
                <th>Category</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {mainProductList.map((mainProduct, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${mainProduct.id}`} color="link" size="sm">
                      {mainProduct.id}
                    </Button>
                  </td>
                  <td>{mainProduct.title}</td>
                  <td>{mainProduct.description}</td>
                  <td>
                    {mainProduct.photo ? (
                      <div>
                        <a onClick={openFile(mainProduct.photoContentType, mainProduct.photo)}>
                          <img src={`data:${mainProduct.photoContentType};base64,${mainProduct.photo}`} style={{ maxHeight: '30px' }} />
                          &nbsp;
                        </a>
                        <span>
                          {mainProduct.photoContentType}, {byteSize(mainProduct.photo)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {mainProduct.categoryId ? (
                      <Link to={`category-my-suffix/${mainProduct.categoryId}`}>{mainProduct.categoryId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${mainProduct.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${mainProduct.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${mainProduct.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ mainProduct }: IRootState) => ({
  mainProductList: mainProduct.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MainProductMySuffix);
