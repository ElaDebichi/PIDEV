<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20210328171338 extends AbstractMigration
{
    public function getDescription() : string
    {
        return '';
    }

    public function up(Schema $schema) : void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE evenements ADD employer_id INT DEFAULT NULL');
        $this->addSql('ALTER TABLE evenements ADD CONSTRAINT FK_E10AD40041CD9E7A FOREIGN KEY (employer_id) REFERENCES user (id)');
        $this->addSql('CREATE INDEX IDX_E10AD40041CD9E7A ON evenements (employer_id)');
    }

    public function down(Schema $schema) : void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE evenements DROP FOREIGN KEY FK_E10AD40041CD9E7A');
        $this->addSql('DROP INDEX IDX_E10AD40041CD9E7A ON evenements');
        $this->addSql('ALTER TABLE evenements DROP employer_id');
    }
}
