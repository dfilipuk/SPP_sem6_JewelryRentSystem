import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomePageComponent } from './components/home-page/home-page.component';
import { EntitiesPageComponent } from './components/entities-page/entities-page.component';
import { DocumentsPageComponent } from './components/documents-page/documents-page.component';

const routes: Routes = [
  { path: '', component: HomePageComponent },
  { path: 'entities', component: EntitiesPageComponent },
  { path: 'documents', component: DocumentsPageComponent },
  { path: '**', component: HomePageComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
