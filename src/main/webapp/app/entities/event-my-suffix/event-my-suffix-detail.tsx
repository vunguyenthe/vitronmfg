import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './event-my-suffix.reducer';
import { IEventMySuffix } from 'app/shared/model/event-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEventMySuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class EventMySuffixDetail extends React.Component<IEventMySuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { eventEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Event [<b>{eventEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="title">Title</span>
            </dt>
            <dd>{eventEntity.title}</dd>
            <dt>
              <span id="newIconPath">New Icon Path</span>
            </dt>
            <dd>
              {eventEntity.newIconPath ? (
                <div>
                  <a onClick={openFile(eventEntity.newIconPathContentType, eventEntity.newIconPath)}>
                    <img
                      src={`data:${eventEntity.newIconPathContentType};base64,${eventEntity.newIconPath}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                  <span>
                    {eventEntity.newIconPathContentType}, {byteSize(eventEntity.newIconPath)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>
              <span id="content">Content</span>
            </dt>
            <dd>{eventEntity.content}</dd>
          </dl>
          <Button tag={Link} to="/entity/event-my-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/event-my-suffix/${eventEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ event }: IRootState) => ({
  eventEntity: event.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EventMySuffixDetail);
