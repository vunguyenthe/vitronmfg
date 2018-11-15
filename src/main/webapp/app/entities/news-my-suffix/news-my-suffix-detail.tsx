import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './news-my-suffix.reducer';
import { INewsMySuffix } from 'app/shared/model/news-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface INewsMySuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class NewsMySuffixDetail extends React.Component<INewsMySuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { newsEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            News [<b>{newsEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="title">Title</span>
            </dt>
            <dd>{newsEntity.title}</dd>
            <dt>
              <span id="description">Description</span>
            </dt>
            <dd>{newsEntity.description}</dd>
            <dt>
              <span id="content">Content</span>
            </dt>
            <dd>{newsEntity.content}</dd>
            <dt>
              <span id="photo">Photo</span>
            </dt>
            <dd>
              {newsEntity.photo ? (
                <div>
                  <a onClick={openFile(newsEntity.photoContentType, newsEntity.photo)}>
                    <img src={`data:${newsEntity.photoContentType};base64,${newsEntity.photo}`} style={{ maxHeight: '30px' }} />
                  </a>
                  <span>
                    {newsEntity.photoContentType}, {byteSize(newsEntity.photo)}
                  </span>
                </div>
              ) : null}
            </dd>
          </dl>
          <Button tag={Link} to="/entity/news-my-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/news-my-suffix/${newsEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ news }: IRootState) => ({
  newsEntity: news.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NewsMySuffixDetail);
