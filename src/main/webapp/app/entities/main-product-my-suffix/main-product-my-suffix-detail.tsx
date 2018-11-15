import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './main-product-my-suffix.reducer';
import { IMainProductMySuffix } from 'app/shared/model/main-product-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMainProductMySuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class MainProductMySuffixDetail extends React.Component<IMainProductMySuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { mainProductEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            MainProduct [<b>{mainProductEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="title">Title</span>
            </dt>
            <dd>{mainProductEntity.title}</dd>
            <dt>
              <span id="description">Description</span>
            </dt>
            <dd>{mainProductEntity.description}</dd>
            <dt>
              <span id="photo">Photo</span>
            </dt>
            <dd>
              {mainProductEntity.photo ? (
                <div>
                  <a onClick={openFile(mainProductEntity.photoContentType, mainProductEntity.photo)}>
                    <img
                      src={`data:${mainProductEntity.photoContentType};base64,${mainProductEntity.photo}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                  <span>
                    {mainProductEntity.photoContentType}, {byteSize(mainProductEntity.photo)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>Category</dt>
            <dd>{mainProductEntity.categoryId ? mainProductEntity.categoryId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/main-product-my-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/main-product-my-suffix/${mainProductEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ mainProduct }: IRootState) => ({
  mainProductEntity: mainProduct.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MainProductMySuffixDetail);
