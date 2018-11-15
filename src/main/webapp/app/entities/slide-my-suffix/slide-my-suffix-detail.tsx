import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './slide-my-suffix.reducer';
import { ISlideMySuffix } from 'app/shared/model/slide-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISlideMySuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class SlideMySuffixDetail extends React.Component<ISlideMySuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { slideEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Slide [<b>{slideEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="slideId">Slide Id</span>
            </dt>
            <dd>
              {slideEntity.slideId ? (
                <div>
                  <a onClick={openFile(slideEntity.slideIdContentType, slideEntity.slideId)}>
                    <img src={`data:${slideEntity.slideIdContentType};base64,${slideEntity.slideId}`} style={{ maxHeight: '30px' }} />
                  </a>
                  <span>
                    {slideEntity.slideIdContentType}, {byteSize(slideEntity.slideId)}
                  </span>
                </div>
              ) : null}
            </dd>
          </dl>
          <Button tag={Link} to="/entity/slide-my-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/slide-my-suffix/${slideEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ slide }: IRootState) => ({
  slideEntity: slide.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(SlideMySuffixDetail);
