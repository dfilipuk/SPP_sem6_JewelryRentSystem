import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './components/app/app.component';
import { HomePageComponent } from './components/home-page/home-page.component';
import { AuthorizationFormComponent } from './components/authorization-form/authorization-form.component';
import { EntitiesPageComponent } from './components/entities-page/entities-page.component';
import { DocumentsPageComponent } from './components/documents-page/documents-page.component';


@NgModule({
  declarations: [
    AppComponent,
    HomePageComponent,
    AuthorizationFormComponent,
    EntitiesPageComponent,
    DocumentsPageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
