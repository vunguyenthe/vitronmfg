import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './product-my-suffix.reducer';
import { IProductMySuffix } from 'app/shared/model/product-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProductMySuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class ProductMySuffixDetail extends React.Component<IProductMySuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { productEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Product [<b>{productEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="title">Title</span>
            </dt>
            <dd>{productEntity.title}</dd>
            <dt>
              <span id="productImagePath">Product Image Path</span>
            </dt>
            <dd>
              {productEntity.productImagePath ? (
                <div>
                  <a onClick={openFile(productEntity.productImagePathContentType, productEntity.productImagePath)}>
                    <img
                      src={`data:${productEntity.productImagePathContentType};base64,${productEntity.productImagePath}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                  <span>
                    {productEntity.productImagePathContentType}, {byteSize(productEntity.productImagePath)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>
              <span id="detailedPdfPath">Detailed Pdf Path</span>
            </dt>
            <dd>
              {productEntity.detailedPdfPath ? (
                <div>
                  <a onClick={openFile(productEntity.detailedPdfPathContentType, productEntity.detailedPdfPath)}>Open&nbsp;</a>
                  <span>
                    {productEntity.detailedPdfPathContentType}, {byteSize(productEntity.detailedPdfPath)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>Category</dt>
            <dd>{productEntity.categoryId ? productEntity.categoryId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/product-my-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/product-my-suffix/${productEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ product }: IRootState) => ({
  productEntity: product.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProductMySuffixDetail);
