<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20210330002355 extends AbstractMigration
{
    public function getDescription() : string
    {
        return '';
    }

    public function up(Schema $schema) : void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE employer_candidat (employer_id INT NOT NULL, candidat_id INT NOT NULL, INDEX IDX_3FF87F2741CD9E7A (employer_id), INDEX IDX_3FF87F278D0EB82 (candidat_id), PRIMARY KEY(employer_id, candidat_id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('ALTER TABLE employer_candidat ADD CONSTRAINT FK_3FF87F2741CD9E7A FOREIGN KEY (employer_id) REFERENCES user (id) ON DELETE CASCADE');
        $this->addSql('ALTER TABLE employer_candidat ADD CONSTRAINT FK_3FF87F278D0EB82 FOREIGN KEY (candidat_id) REFERENCES user (id) ON DELETE CASCADE');
        $this->addSql('ALTER TABLE user ADD nbr_follow INT NOT NULL');
    }

    public function down(Schema $schema) : void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('DROP TABLE employer_candidat');
        $this->addSql('ALTER TABLE user DROP nbr_follow');
    }
}
