import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import NewsMySuffix from './news-my-suffix';
import NewsMySuffixDetail from './news-my-suffix-detail';
import NewsMySuffixUpdate from './news-my-suffix-update';
import NewsMySuffixDeleteDialog from './news-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={NewsMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={NewsMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={NewsMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={NewsMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={NewsMySuffixDeleteDialog} />
  </>
);

export default Routes;
