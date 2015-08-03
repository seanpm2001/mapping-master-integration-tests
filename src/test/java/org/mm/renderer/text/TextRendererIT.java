package org.mm.renderer.text;

import junit.framework.Assert;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WriteException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mm.exceptions.MappingMasterException;
import org.mm.parser.ParseException;
import org.mm.renderer.RendererException;
import org.mm.rendering.text.TextRendering;
import org.mm.ss.SpreadsheetLocation;
import org.mm.test.IntegrationTestBase;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

public class TextRendererIT extends IntegrationTestBase
{
  @Rule public final ExpectedException thrown = ExpectedException.none();

  @Test public void TestClassDeclaration()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: Car";
    Optional<? extends TextRendering> textRendering = createTextRendering(expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expression, textRendering.get().getRendering());
  }

  @Test public void TestSubClassOf()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: Car SubClassOf: Vehicle";
    Optional<? extends TextRendering> textRendering = createTextRendering(expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expression, textRendering.get().getRendering());
  }

  @Test public void TestMultipleSubClassOf()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: Car SubClassOf: Vehicle, Device";
    Optional<? extends TextRendering> textRendering = createTextRendering(expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expression, textRendering.get().getRendering());
  }

  @Test public void TestEquivalentToClass()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: Car EquivalentTo: Automobile";
    Optional<? extends TextRendering> textRendering = createTextRendering(expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expression, textRendering.get().getRendering());
  }

  @Test public void TestEquivalentToClassExpression()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: Car EquivalentTo: (hasEngine EXACTLY 1)";
    Optional<? extends TextRendering> textRendering = createTextRendering(expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expression, textRendering.get().getRendering());
  }

  @Test public void TestMultipleEquivalentClass()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: Car EquivalentTo: Automobile, Auto, (hasEngine EXACTLY 1)";
    Optional<? extends TextRendering> textRendering = createTextRendering(expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expression, textRendering.get().getRendering());
  }

  @Test public void TestClassDeclarationWithAnnotations()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: Car Annotations: hasAuthor Bob";
    Optional<? extends TextRendering> textRendering = createTextRendering(expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expression, textRendering.get().getRendering());
  }

  @Test public void TestClassDeclarationWithMultipleAnnotations()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: Car Annotations: hasAuthor Bob, hasDate \"1990-10-10\"";
    Optional<? extends TextRendering> textRendering = createTextRendering(expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expression, textRendering.get().getRendering());
  }

  @Test public void TestMaxCardinalityRestriction()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: Car SubClassOf: (hasEngine MAX 1)";
    Optional<? extends TextRendering> textRendering = createTextRendering(expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expression, textRendering.get().getRendering());
  }

  @Test public void TestMinCardinalityRestriction()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: Car SubClassOf: (hasSSN MIN 1)";
    Optional<? extends TextRendering> textRendering = createTextRendering(expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expression, textRendering.get().getRendering());
  }

  @Test public void TestExactCardinalityRestriction()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: Car SubClassOf: (hasSSN EXACTLY 1)";
    Optional<? extends TextRendering> textRendering = createTextRendering(expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expression, textRendering.get().getRendering());
  }

  @Test public void TestObjectHasValueRestriction()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: Catamaran SubClassOf: (hasHull VALUE 2)"; // XXX: Should be Individual
    Optional<? extends TextRendering> textRendering = createTextRendering(expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expression, textRendering.get().getRendering());
  }

  @Test public void TestDataHasValueRestriction()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: BMW SubClassOf: (hasOrigin VALUE \"Germany\")";
    Optional<? extends TextRendering> textRendering = createTextRendering(expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expression, textRendering.get().getRendering());
  }

  @Test public void TestObjectSomeValueRestriction()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: ChildOfDoctor SubClassOf: (hasParent SOME Physician)";
    Optional<? extends TextRendering> textRendering = createTextRendering(expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expression, textRendering.get().getRendering());
  }

  @Test public void TestDataSomeValueRestriction()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: Car SubClassOf: (hasName SOME xsd:string)";
    Optional<? extends TextRendering> textRendering = createTextRendering(expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expression, textRendering.get().getRendering());
  }

  @Test public void TestObjectAllValuesFromRestriction() // XXX: Minor mistake on naming method
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: Person SubClassOf: (hasSSN ONLY xsd:string)";
    Optional<? extends TextRendering> textRendering = createTextRendering(expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expression, textRendering.get().getRendering());
  }

  @Test public void TestDataAllValuesFromRestriction()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: Person SubClassOf: (hasParent ONLY Human)";
    Optional<? extends TextRendering> textRendering = createTextRendering(expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expression, textRendering.get().getRendering());
  }

  // TODO The grammar is incorrect here - parenthesis should not be required around the {}.
  @Test public void TestOWLObjectOneOf()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: Person SubClassOf: (hasGender ONLY ({Male, Female, Other}))";
    Optional<? extends TextRendering> textRendering = createTextRendering(expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expression, textRendering.get().getRendering());
  }

  @Test public void TestNegatedClassExpression()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: Car EquivalentTo: NOT (hasEngine EXACTLY 2)";
    Optional<? extends TextRendering> textRendering = createTextRendering(expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expression, textRendering.get().getRendering());
  }

  @Test public void TestUnionClassExpression()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: A SubClassOf: ((hasP1 EXACTLY 2) OR (hasP2 EXACTLY 3))";
    Optional<? extends TextRendering> textRendering = createTextRendering(expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expression, textRendering.get().getRendering());
  }

  @Test public void TestIntersectionClassExpression()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: A SubClassOf: ((hasP1 EXACTLY 2) AND (hasP2 EXACTLY 3))";
    Optional<? extends TextRendering> textRendering = createTextRendering(expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expression, textRendering.get().getRendering());
  }

  @Test public void TestIndividualDeclaration()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Individual: Fred";
    Optional<? extends TextRendering> textRendering = createTextRendering(expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expression, textRendering.get().getRendering());
  }

  @Test public void TestIndividualDeclarationWithTypes()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Individual: Fred Types: Person";
    Optional<? extends TextRendering> textRendering = createTextRendering(expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expression, textRendering.get().getRendering());
  }

  @Test public void TestIndividualDeclarationWithMultipleTypes()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Individual: Fred Types: Person, (hasParent ONLY Human)";
    Optional<? extends TextRendering> textRendering = createTextRendering(expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expression, textRendering.get().getRendering());
  }

  @Test public void TestIndividualDeclarationWithFacts()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Individual: Fred Facts: hasName \"Fred\"";
    Optional<? extends TextRendering> textRendering = createTextRendering(expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expression, textRendering.get().getRendering());
  }

  @Test public void TestIndividualDeclarationWithMultipleFacts()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Individual: Fred Facts: hasName \"Fred\", hasAge 23";
    Optional<? extends TextRendering> textRendering = createTextRendering(expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expression, textRendering.get().getRendering());
  }

  @Test public void TestIndividualDeclarationWithAnnotations()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Individual: Fred Annotations: hasName \"Fred\"";
    Optional<? extends TextRendering> textRendering = createTextRendering(expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expression, textRendering.get().getRendering());
  }

  @Test public void TestIndividualDeclarationWithMultipleAnnotations()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Individual: Fred Annotations: hasName \"Fred\", hasAge 23";
    Optional<? extends TextRendering> textRendering = createTextRendering(expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expression, textRendering.get().getRendering());
  }

  @Test public void TestIndividualDeclarationWithSameIndividual()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Individual: Fred SameAs: Freddy";
    Optional<? extends TextRendering> textRendering = createTextRendering(expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expression, textRendering.get().getRendering());
  }

  @Test public void TestIndividualDeclarationWithMultipleSameIndividual()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Individual: Fred SameAs: Freddy, F";
    Optional<? extends TextRendering> textRendering = createTextRendering(expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expression, textRendering.get().getRendering());
  }

  @Test public void TestIndividualDeclarationWithDifferentIndividuals()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Individual: Fred DifferentFrom: Bob";
    Optional<? extends TextRendering> textRendering = createTextRendering(expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expression, textRendering.get().getRendering());
  }

  @Test public void TestIndividualDeclarationWithMultipleDifferentIndividuals()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Individual: Fred DifferentFrom: Bob, Bobby";
    Optional<? extends TextRendering> textRendering = createTextRendering(expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expression, textRendering.get().getRendering());
  }

  @Test public void TestAbsoluteClassReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: @A1";
    String expectedRendering = "Class: Car";
    Label cellA1 = createCell("Car", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestAbsoluteIndividualReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Individual: @A1";
    String expectedRendering = "Individual: Car";
    Label cellA1 = createCell("Car", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestAbsoluteObjectPropertyReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Individual: Fred Facts: @A1 Bob";
    String expectedRendering = "Individual: Fred Facts: hasUncle Bob";
    Label cellA1 = createCell("hasUncle", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestAbsoluteDataPropertyReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Individual: Fred Facts: @A1 23";
    String expectedRendering = "Individual: Fred Facts: hasAge 23";
    Label cellA1 = createCell("hasAge", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestAbsoluteAnnotationPropertyReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Individual: Fred Annotations: @A1 23";
    String expectedRendering = "Individual: Fred Annotations: hasAge 23";
    Label cellA1 = createCell("hasAge", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestLiteralReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: @\"Car\"";
    String expectedRendering = "Class: Car";
    Optional<? extends TextRendering> textRendering = createTextRendering(expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestAbsoluteReferenceWithSheetName()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: @'" + SHEET1 + "'!A1";
    String expectedRendering = "Class: Car";
    Label cellA1 = createCell("Car", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestColumnWildcardInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: @*1";
    String expectedRendering = "Class: Car";
    Label cellA1 = createCell("Car", 1, 1);
    Set<Label> cells = createCells(cellA1);
    SpreadsheetLocation currentLocation = new SpreadsheetLocation(SHEET1, 1, 1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, currentLocation, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestRowWildcardInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: @A*";
    String expectedRendering = "Class: Car";
    Label cellA1 = createCell("Car", 1, 1);
    Set<Label> cells = createCells(cellA1);
    SpreadsheetLocation currentLocation = new SpreadsheetLocation(SHEET1, 1, 1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, currentLocation, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestClassQualifiedInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: @A1(Class)";
    String expectedRendering = "Class: Car";
    Label cellA1 = createCell("Car", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestIndividualInQualifiedReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Individual: @A1(Individual)";
    String expectedRendering = "Individual: Car";
    Label cellA1 = createCell("Car", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestObjectPropertyInQualifiedReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Individual: Fred Facts: @A1(ObjectProperty) Bob";
    String expectedRendering = "Individual: Fred Facts: hasUncle Bob";
    Label cellA1 = createCell("hasUncle", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestDataPropertyQualifiedInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Individual: Fred Facts: @A1(DataProperty) 23";
    String expectedRendering = "Individual: Fred Facts: hasAge 23";
    Label cellA1 = createCell("hasAge", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestAnnotationPropertyQualifiedReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Individual: Fred Annotations: @A1(AnnotationProperty) 23";
    String expectedRendering = "Individual: Fred Annotations: hasAge 23";
    Label cellA1 = createCell("hasAge", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestXSDBooleanInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Individual: Fred Facts: hasSSN @A1(xsd:boolean)";
    String expectedRendering = "Individual: Fred Facts: hasSSN true";
    Label cellA1 = createCell("true", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestXSDByteInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Individual: Fred Facts: hasSalary @A1(xsd:byte)";
    String expectedRendering = "Individual: Fred Facts: hasSalary 34";
    Label cellA1 = createCell("34", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestXSDShortInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Individual: Fred Facts: hasSalary @A1(xsd:short)";
    String expectedRendering = "Individual: Fred Facts: hasSalary 34";
    Label cellA1 = createCell("34", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestXSDIntInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Individual: Fred Facts: hasSalary @A1(xsd:int)";
    String expectedRendering = "Individual: Fred Facts: hasSalary 34";
    Label cellA1 = createCell("34", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestXSDFloatInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Individual: Fred Facts: hasSalary @A1(xsd:float)";
    String expectedRendering = "Individual: Fred Facts: hasSalary 34000.0";
    Label cellA1 = createCell("34000.0", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestXSDStringInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Individual: Fred Facts: hasName @A1(xsd:string)";
    String expectedRendering = "Individual: Fred Facts: hasName \"Fred\"";
    Label cellA1 = createCell("Fred", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestXSDDateInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Individual: Fred Facts: hasDOB @A1(xsd:date)";
    String expectedRendering = "Individual: Fred Facts: hasDOB \"1999-01-01\"";
    Label cellA1 = createCell("1999-01-01", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestXSDDateTimeInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Individual: Fred Facts: hasDOB @A1(xsd:dateTime)";
    String expectedRendering = "Individual: Fred Facts: hasDOB \"1999-01-01T10:10:10\"";
    Label cellA1 = createCell("1999-01-01T10:10:10", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestXSDTimeInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Individual: Fred Facts: hasBedTime @A1(xsd:time)";
    String expectedRendering = "Individual: Fred Facts: hasBedTime \"21:00\"";
    Label cellA1 = createCell("21:00", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestRDFSLabelAssignmentInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: @A1(rdfs:label=(\"Big\"))";
    String expectedRendering = "Class: Big";
    Label cellA1 = createCell("Car", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestRDFSLabelAssignmentWithConcatenatedParametersInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: @A1(rdfs:label=(\"Big\", \"Car\"))";
    String expectedRendering = "Class: BigCar";
    Label cellA1 = createCell("Car", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestRDFSLabelAssignmentWithReferenceParameterInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: @A1(rdfs:label=(\"Big\", @A1))";
    String expectedRendering = "Class: BigCar";
    Label cellA1 = createCell("Car", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestRDFSLabelAppendInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: @A1(rdfs:label=mm:append(\"Big\"))";
    String expectedRendering = "Class: CarBig";
    Label cellA1 = createCell("Car", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestRDFSLabelPrependInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: @A1(rdfs:label=mm:prepend(\"Big\"))";
    String expectedRendering = "Class: BigCar";
    Label cellA1 = createCell("Car", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestDefaultPrependInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: @A1(mm:prepend(\"Big\"))";
    String expectedRendering = "Class: BigCar";
    Label cellA1 = createCell("Car", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestShiftUpInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: @A4(mm:ShiftUp)";
    String expectedRendering = "Class: Car";
    Label cellA1 = createCell("Car", 1, 1);
    Label cellA2 = createCell("", 1, 2);
    Label cellA3 = createCell("", 1, 3);
    Label cellA4 = createCell("", 1, 4);
    Set<Label> cells = createCells(cellA1, cellA2, cellA3, cellA4);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestShiftDownInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: @A1(mm:ShiftDown)";
    String expectedRendering = "Class: Car";
    Label cellA1 = createCell("", 1, 1);
    Label cellA2 = createCell("", 1, 2);
    Label cellA3 = createCell("", 1, 3);
    Label cellA4 = createCell("Car", 1, 4);
    Set<Label> cells = createCells(cellA1, cellA2, cellA3, cellA4);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestReferencesInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: @A*(mm:append(@B*(mm:ShiftDown), @C*(mm:ShiftRight)))";
    String expectedRendering = "Class: BMWGermany";
    Label cellA1 = createCell("Car", 1, 1);
    Label cellB1 = createCell("", 2, 2);
    Label cellB2 = createCell("", 2, 3);
    Label cellB3 = createCell("", 3, 4);
    Label cellB4 = createCell("BMW", 2, 4);
    Label cellC1 = createCell("", 3, 1);
    Label cellD1 = createCell("", 4, 1);
    Label cellE1 = createCell("", 5, 1);
    Label cellF1 = createCell("Germany", 6, 1);
    Set<Label> cells = createCells(cellA1, cellB1, cellB2, cellB3, cellB4, cellC1, cellD1, cellE1, cellF1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestShiftRightInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: @A1(mm:ShiftRight)";
    String expectedRendering = "Class: Car";
    Label cellA1 = createCell("", 1, 1);
    Label cellB1 = createCell("", 2, 1);
    Label cellC1 = createCell("", 3, 1);
    Label cellD1 = createCell("Car", 4, 1);
    Set<Label> cells = createCells(cellA1, cellB1, cellC1, cellD1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestShiftLeftInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: @D1(mm:ShiftLeft)";
    String expectedRendering = "Class: Car";
    Label cellA1 = createCell("Car", 1, 1);
    Label cellB1 = createCell("", 2, 1);
    Label cellC1 = createCell("", 3, 1);
    Label cellD1 = createCell("", 4, 1);
    Set<Label> cells = createCells(cellA1, cellB1, cellC1, cellD1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestToLowerCaseInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: @A1(mm:toLowerCase(\"CAR\"))";
    String expectedRendering = "Class: car";
    Label cellA1 = createCell("Car", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestTrimInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: @A1(mm:trim(\"  Car  \"))";
    String expectedRendering = "Class: Car";
    Label cellA1 = createCell("Car", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestReverseInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: @A1(mm:reverse(\"raC\"))";
    String expectedRendering = "Class: Car";
    Label cellA1 = createCell("Car", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestToUpperCaseInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: @A1(mm:toUpperCase(@A1))";
    String expectedRendering = "Class: CAR";
    Label cellA1 = createCell("car", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestToUpperCaseImplicitInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: @A1(mm:toUpperCase)";
    String expectedRendering = "Class: CAR";
    Label cellA1 = createCell("car", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestReplaceAllInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Individual: Fred Facts: hasItem @A1(mm:replaceAll(\"[^a-zA-Z0-9]\",\"\"))";
    String expectedRendering = "Individual: Fred Facts: hasItem \"bag\"";
    Label cellA1 = createCell(")(*bag%^&$#@!", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestCapturingExpressionInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: @A1(rdfs:label=[\":(\\S+)\"])";
    String expectedRendering = "Class: Zyvox";
    Label cellA1 = createCell("Pfizer:Zyvox", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestCapturingExpressionStandaloneInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: @A1([\":(\\S+)\"])";
    String expectedRendering = "Class: Zyvox";
    Label cellA1 = createCell("Pfizer:Zyvox", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestCapturingExpressionMethodInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: @A1(rdfs:label=mm:capturing(\":(\\S+)\"))";
    String expectedRendering = "Class: Zyvox";
    Label cellA1 = createCell("Pfizer:Zyvox", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestMultipleCapturingExpressionsInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Individual: Fred  " +
        "Facts: " +
        "hasMin @A1(xsd:int [\"(\\d+)\\s+\"]), " +
        "hasMax @A1(xsd:int [\"\\s+(\\d+)\"])" +
        "Types: Person";
    String expectedRendering = "Individual: Fred Facts: hasMin 23, hasMax 44 Types: Person";
    Label cellA1 = createCell("23 44", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestDefaultLocationValueInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: @A1(mm:DefaultLocationValue=\"Unknown\")";
    String expectedRendering = "Class: Unknown";
    Label cellA1 = createCell("", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestDefaultLabelInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: @A1(rdfs:label mm:DefaultLabel=\"Unknown\")";
    String expectedRendering = "Class: Unknown";
    Label cellA1 = createCell("", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestResolveIfOWLEntityExistsInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: @A1(mm:ResolveIfOWLEntityExists)";
    String expectedRendering = "Class: Car";
    Label cellA1 = createCell("Car", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestSkipIfOWLEntityExistsInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: @A1(mm:SkipIfOWLEntityExists)";
    String expectedRendering = "Class: Car";
    Label cellA1 = createCell("Car", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestWarningIfOWLEntityExistsInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: @A1(mm:WarningIfOWLEntityExists)";
    String expectedRendering = "Class: Car";
    Label cellA1 = createCell("Car", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestErrorIfOWLEntityExistsInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: @A1(mm:ErrorIfOWLEntityExists)";
    String expectedRendering = "Class: Car";
    Label cellA1 = createCell("Car", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestCreateIfOWLEntityDoesNotExistInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: @A1(mm:CreateIfOWLEntityDoesNotExist)";
    String expectedRendering = "Class: Car";
    Label cellA1 = createCell("Car", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestSkipIfOWLEntityDoesNotExistInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: @A1(mm:SkipIfOWLEntityDoesNotExist)";
    String expectedRendering = "Class: Car";
    Label cellA1 = createCell("Car", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestWarningIfOWLEntityDoesNotExistInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: @A1(mm:WarningIfOWLEntityDoesNotExist)";
    String expectedRendering = "Class: Car";
    Label cellA1 = createCell("Car", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestErrorIfOWLEntityDoesNotExistInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: @A1(mm:ErrorIfOWLEntityDoesNotExist)";
    String expectedRendering = "Class: Car";
    Label cellA1 = createCell("Car", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestErrorIfEmptyLocationDirectiveInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    this.thrown.expect(RendererException.class);
    this.thrown.expectMessage("empty location");

    String expression = "Class: @A1(mm:ErrorIfEmptyLocation)";
    Label cellA1 = createCell("", 1, 1);
    Set<Label> cells = createCells(cellA1);
    createTextRendering(SHEET1, cells, expression);
  }

  @Test public void TestErrorIfEmptyLiteralDirectiveInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    this.thrown.expect(RendererException.class);
    this.thrown.expectMessage("empty literal in reference");

    String expression = "Individual: Fred Facts: hasName @A1(xsd:string mm:ErrorIfEmptyLiteral)";
    Label cellA1 = createCell("", 1, 1);
    Set<Label> cells = createCells(cellA1);
    createTextRendering(SHEET1, cells, expression);
  }

  @Test public void TestErrorIfEmptyRDFSLabelDirectiveInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    this.thrown.expect(RendererException.class);
    this.thrown.expectMessage("empty RDFS label in reference");

    String expression = "Class: @A1(mm:ErrorIfEmptyLabel)";
    Label cellA1 = createCell("", 1, 1);
    Set<Label> cells = createCells(cellA1);
    createTextRendering(SHEET1, cells, expression);
  }

  @Test public void TestErrorIfEmptyRDFIDDirectiveInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    this.thrown.expect(RendererException.class);
    this.thrown.expectMessage("empty RDF ID in reference");

    String expression = "Class: @A1(rdf:ID=@A1 mm:ErrorIfEmptyID)";
    Label cellA1 = createCell("", 1, 1);
    Set<Label> cells = createCells(cellA1);
    createTextRendering(SHEET1, cells, expression);
  }

  @Test public void TestSkipIfEmptyLocationInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Individual: Fred Facts: hasName @A1(xsd:string mm:SkipIfEmptyLocation)";
    String expectedRendering = "Individual: Fred";
    Label cellA1 = createCell("", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestSkipIfEmptyLiteralInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Individual: Fred Facts: hasName @A1(xsd:string mm:SkipIfEmptyLiteral)";
    String expectedRendering = "Individual: Fred";
    Label cellA1 = createCell("", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestSkipIfEmptyRDFSLabelDirectiveInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: @A1(mm:SkipIfEmptyLabel)";
    Label cellA1 = createCell("", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertFalse(textRendering.isPresent());
  }

  @Test public void TestSkipIfEmptyRDFIDDirectiveInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: @A1(rdf:ID mm:SkipIfEmptyID)";
    Label cellA1 = createCell("", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertFalse(textRendering.isPresent());
  }

  @Test public void TestWarningIfEmptyLocationInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Individual: Fred Facts: hasName @A1(xsd:string mm:WarningIfEmptyLocation)";
    String expectedRendering = "Individual: Fred";
    Label cellA1 = createCell("", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestWarningIfEmptyLiteralInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Individual: Fred Facts: hasName @A1(xsd:string mm:WarningIfEmptyLiteral)";
    String expectedRendering = "Individual: Fred";
    Label cellA1 = createCell("", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestWarningIfEmptyRDFSLabelDirectiveInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: @A1(mm:WarningIfEmptyLabel)";
    Label cellA1 = createCell("", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertFalse(textRendering.isPresent());
  }

  @Test public void TestWarningIfEmptyRDFIDDirectiveInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Class: @A1(rdf:ID mm:WarningIfEmptyID)";
    Label cellA1 = createCell("", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertFalse(textRendering.isPresent());
  }

  @Test public void TestProcessIfEmptyLiteralInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    String expression = "Individual: Fred Facts: hasName @A1(xsd:string mm:ProcessIfEmptyLiteral)";
    String expectedRendering = "Individual: Fred Facts: hasName \"\"";
    Label cellA1 = createCell("", 1, 1);
    Set<Label> cells = createCells(cellA1);
    Optional<? extends TextRendering> textRendering = createTextRendering(SHEET1, cells, expression);

    Assert.assertTrue(textRendering.isPresent());
    Assert.assertEquals(expectedRendering, textRendering.get().getRendering());
  }

  @Test public void TestOutOfRangeColumnInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    this.thrown.expect(RendererException.class);
    this.thrown.expectMessage("invalid source specification @D1 - column D out of range");

    String expression = "Class: @D1";
    Label cellA1 = createCell("Car", 1, 1);
    Set<Label> cells = createCells(cellA1);
    createTextRendering(SHEET1, cells, expression);
  }

  @Test public void TestOutOfRangeRowInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    this.thrown.expect(RendererException.class);
    this.thrown.expectMessage("invalid source specification @A3 - row 3 out of range");

    String expression = "Class: @A3";
    Label cellA1 = createCell("Car", 1, 1);
    Set<Label> cells = createCells(cellA1);
    createTextRendering(SHEET1, cells, expression);
  }

  @Test public void TestInvalidSheetNameInReference()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    this.thrown.expect(RendererException.class);
    this.thrown.expectMessage("invalid sheet name fff");

    String expression = "Class: @'fff'!A3";
    createTextRendering(SHEET1, expression);
  }

  @Test public void TestParseException()
      throws WriteException, BiffException, MappingMasterException, ParseException, IOException
  {
    this.thrown.expect(ParseException.class);

    String expression = "Class: @";
    createTextRendering(expression);
  }
}
