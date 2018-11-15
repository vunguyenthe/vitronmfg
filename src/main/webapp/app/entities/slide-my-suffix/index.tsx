import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SlideMySuffix from './slide-my-suffix';
import SlideMySuffixDetail from './slide-my-suffix-detail';
import SlideMySuffixUpdate from './slide-my-suffix-update';
import SlideMySuffixDeleteDialog from './slide-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SlideMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SlideMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SlideMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={SlideMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={SlideMySuffixDeleteDialog} />
  </>
);

export default Routes;
